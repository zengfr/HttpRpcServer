package crawler.httpRpcServer.process;

import java.util.List;
import java.util.Map.Entry;

import io.netty.handler.codec.http.HttpHeaders;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public interface HttpRpcCommandProcess {
	String execCmd(String type,String cmd,String[] args,List<Entry<CharSequence, CharSequence>> heads, String content) throws Exception;
}
