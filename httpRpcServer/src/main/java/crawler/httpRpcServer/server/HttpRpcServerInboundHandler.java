package crawler.httpRpcServer.server;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import crawler.httpRpcServer.process.HttpRpcCommandProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpRequest;
/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class HttpRpcServerInboundHandler extends ChannelHandlerAdapter {
	private static Log log = LogFactory.getLog(HttpRpcServerInboundHandler.class);
	private static final String defaultEncoding = "UTF-8";
    private HttpRequest request;
    private HttpRpcCommandProcess commandProcess;
    public HttpRpcServerInboundHandler(HttpRpcCommandProcess httpRpcCommandProcess){
    	this.commandProcess=httpRpcCommandProcess;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
            String uri = request.uri();
            log.info(String.format("Uri:%s", uri));
        }
        if (msg instanceof HttpMessage) {
        	HttpMessage  httpMessage = (HttpMessage) msg;
            log.info(String.format("Message:%s",httpMessage));
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String contentString=buf.toString(Charset.forName(defaultEncoding));
            log.info(String.format("Content:%s",contentString));
            buf.release();
            
            String uri = request.uri();
            List<Entry<CharSequence, CharSequence>> heads = request.headers().entries();
            
            HttpRpcServerCommandAdapter commandAdapter =new HttpRpcServerCommandAdapter();
            String rtn=commandAdapter.parseAndProcess(commandProcess,uri,heads,contentString);
            
            String res = String.format("%s",rtn);rtn=null;
            log.info(String.format("resp:%s", res));
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(res.getBytes(defaultEncoding)));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
            if (HttpHeaderUtil.isKeepAlive(request)) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
        ctx.close();
    }
}
