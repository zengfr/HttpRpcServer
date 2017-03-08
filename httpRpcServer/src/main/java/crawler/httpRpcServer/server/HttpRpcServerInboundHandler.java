package crawler.httpRpcServer.server;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import crawler.httpRpcServer.process.HttpRpcCommandProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class HttpRpcServerInboundHandler extends ChannelInboundHandlerAdapter {
	private static Log log = LogFactory.getLog(HttpRpcServerInboundHandler.class);

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
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String contentString=buf.toString(io.netty.util.CharsetUtil.UTF_8);
            log.info(String.format("Content:%s",contentString));
            buf.release();
            HttpRpcServerCommandAdapter commandAdapter =new HttpRpcServerCommandAdapter();
            String rtn=commandAdapter.parseAndProcess(commandProcess,contentString);
            
            String res = String.format("%s,%s,%s","OK",new Date(),rtn);
            log.info(String.format("resp:%s", res));
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
            if (HttpUtil.isKeepAlive(request)) {
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
