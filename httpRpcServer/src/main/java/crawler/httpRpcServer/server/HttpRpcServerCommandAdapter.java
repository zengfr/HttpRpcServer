package crawler.httpRpcServer.server;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import crawler.httpRpcServer.process.HttpRpcCommandProcess;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class HttpRpcServerCommandAdapter {
	private static Log log = LogFactory.getLog(HttpRpcServerCommandAdapter.class);

	public String parseAndProcess(HttpRpcCommandProcess commandProcess, String body) throws Exception {
		String rtn = "Empty";
		if (body == null)
			return "NULL";
		if (!body.startsWith("?"))
			body = "?" + body;
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(body);
		Map<String, List<String>> params = queryStringDecoder.parameters();
		String token = "";
		String type = "";
		String cmd = "";
		String[] args = null;
		if (!params.isEmpty()) {
			if (params.containsKey("token")) {
				token = StringUtils.join(params.get("token"), " ");
			}
			if (params.containsKey("type")) {
				type = StringUtils.join(params.get("type"), " ");
			}
			if (params.containsKey("cmd")) {
				cmd = StringUtils.join(params.get("cmd"), " ");
			}
			if (params.containsKey("args")) {
				args = StringUtils.join(params.get("args"), " ").split(" ");
			}
			log.info(String.format("type:%s,cmd:%s,args:%s,token:%s", type, cmd, args, token));
			if (token != null && !token.isEmpty() && type != null && !type.isEmpty()) {
				String result = commandProcess.execCmd(type, cmd, args);
				rtn += String.format("callback:%s", result);
			}
		}
		return rtn;
	}
}
