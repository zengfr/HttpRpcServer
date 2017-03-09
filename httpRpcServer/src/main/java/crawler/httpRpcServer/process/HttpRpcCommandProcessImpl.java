package crawler.httpRpcServer.process;

import java.util.List;
import java.util.Map.Entry;

import crawler.httpRpcServer.command.CommandCallback;
import crawler.httpRpcServer.command.CommandExecUtil;
import crawler.httpRpcServer.command.CommandHttpUtil;
import crawler.httpRpcServer.command.CommandPppoeUtil;
import io.netty.handler.codec.http.HttpHeaders;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class HttpRpcCommandProcessImpl  implements  HttpRpcCommandProcess{

	@Override
	public String execCmd(String type, String cmd,String[] args,List<Entry<CharSequence, CharSequence>> heads, String content) throws Exception {
		CommandCallback cmdCallback = new CommandCallback();
		type=type.toLowerCase();
		switch (type) {
		case "http":
			CommandHttpUtil.exec(cmd,args,heads,content, cmdCallback);
			break;
		case "exec":
			CommandExecUtil.execCmd(cmd, cmdCallback);
			break;
		case "cmd":
			CommandExecUtil.execCmd("cmd /c " + cmd, cmdCallback);
			break;
		case "pppoestop":
			CommandPppoeUtil.pppoeWinStop(cmdCallback);
			break;
		case "pppoestart":
			String adslUser = "";
			String adslPassword = "";
			if (args!=null) {
				if (args.length>0) {
					adslUser =args[0];
				}
				if (args.length>1) {
					adslPassword =args[1];
				}
			}
            if(adslUser.isEmpty()&&adslUser.isEmpty()){
            	CommandPppoeUtil.pppoeWinStart(cmdCallback);
            }
            else{
			   CommandPppoeUtil.pppoeWinStart(adslUser, adslPassword, cmdCallback);
            }
			break;
		default:
			break;
		}
		return cmdCallback.getResult();
	}

}
