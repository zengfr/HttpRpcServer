package crawler.httpRpcServer.command;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class CommandPppoeUtil {
	private static Log log = LogFactory.getLog(CommandExecUtil.class);
	private static final String PppoeName="宽带连接";
	public static void pppoeWinStop(CommandCallback callback) throws IOException, InterruptedException {
		log.info("rasdial /disconnect");
		CommandExecUtil.execCmd("rasdial /disconnect", callback);
		log.info("rasdial /disconnect cmd sent");
		log.info("rasphone -h "+PppoeName);
		CommandExecUtil.execCmd("rasphone -h "+PppoeName, callback);
		log.info("rasphone -h "+PppoeName+"  cmd sent");
	}

	public static void pppoeWinStart(CommandCallback callback) throws IOException, InterruptedException {
		String adslUser = "";
		String adslPassword = "";
		adslUser =getProperty("adslUser");
		adslPassword =getProperty("adslPassword");
		pppoeWinStart(adslUser, adslPassword, callback);
	}
	public static String getProperty(String key) throws IOException {
		ClassLoader classLoader = CommandPppoeUtil.class.getClassLoader();
		String url = null;
		if (classLoader.getResource("/") != null) {
			url = classLoader.getResource("/").getPath();
		} else if (classLoader.getResource("") != null) {
			url = classLoader.getResource("").getPath();
		}

		Properties pps = new Properties();
		if (url == null || url.isEmpty()) {
			pps.load(classLoader.getResourceAsStream("app.properties"));
		}
		  else {
			pps.load(new FileInputStream(url + "/" + "app.properties"));
		}
		String v=pps.getProperty(key);pps=null;
		return v;
	}
	public static void pppoeWinStart(String adslUser, String adslPassword, CommandCallback callback)
			throws IOException, InterruptedException {
		log.info("rasdial connect "+adslUser);
		StringBuilder connectcmd = new StringBuilder("rasdial \""+PppoeName+"\" ");
		connectcmd.append(adslUser);
		connectcmd.append(" ");
		connectcmd.append(adslPassword);

		CommandExecUtil.execCmd(connectcmd.toString(), callback);
		log.info("rasdial connect command sent");
	}
}
