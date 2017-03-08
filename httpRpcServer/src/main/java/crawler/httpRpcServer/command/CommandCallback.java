package crawler.httpRpcServer.command;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class CommandCallback {
	private static Log log = LogFactory.getLog(CommandCallback.class);
	private static final String encoding = "gbk";
	private String result = "";

	public void stderrCallback(InputStream stream) {
		String text = "";
		try {
			text = IOUtils.toString(stream, encoding);
			result += String.format(" err:%s", text);
		} catch (IOException e) {
			e.printStackTrace();
			result += String.format(" ex:%s", e);
		}
		log.info(text);
	}

	public void stdinCallBack(InputStream stream) {
		String text = "";
		try {
			text = IOUtils.toString(stream, encoding);
			result += String.format(" in:%s", text);
		} catch (IOException e) {
			e.printStackTrace();
			result += String.format(" ex:%s", e);
		}
		log.info(text);
	}

	public String getResult() {
		return result;
	}
}
