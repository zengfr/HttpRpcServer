package crawler.httpRpcServer.process;

import crawler.httpRpcServer.command.CommandCallback;
import crawler.httpRpcServer.command.CommandExecUtil;
import crawler.httpRpcServer.command.CommandPppoeUtil;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class HttpRpcCommandProcessImpl  implements  HttpRpcCommandProcess{

	@Override
	public String execCmd(String type, String cmd, String... args) throws Exception {
		CommandCallback cmdCallback = new CommandCallback();
		type=type.toLowerCase();
		switch (type) {
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
