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
	private String result = "";
	public CommandCallback (){
		
	}
	public void stderrCallback(String encoding,InputStream stream) {
		String text = "";
		try {
			text = IOUtils.toString(stream, encoding);
			result += String.format("%s", text);
		} catch (IOException e) {
			e.printStackTrace();
			result += String.format("%s", e);
		}
		log.error(text);
	}

	public void stdinCallBack(String encoding,InputStream stream) {
		String text = "";
		try {
			text = IOUtils.toString(stream, encoding);
			result += String.format("%s", text);
		} catch (IOException e) {
			e.printStackTrace();
			result += String.format("%s", e);
		}
		log.debug(text);
	}

	public String getResult() {
		return result;
	}
}
