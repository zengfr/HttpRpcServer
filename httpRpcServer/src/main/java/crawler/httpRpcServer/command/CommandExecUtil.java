package crawler.httpRpcServer.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class CommandExecUtil {
	private static Log log = LogFactory.getLog(CommandExecUtil.class);
	private static final String defaultEncoding = "UTF-8";
	public static boolean isOSWindows() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			return true;
		}
		return false;
	}

	public static void execCmd(String command, final CommandCallback cmdCallback) throws IOException, InterruptedException //
	{
		Process p = null;
		Runtime rt = Runtime.getRuntime();
		if (isOSWindows()) {

			try {

				p = rt.exec(command);
				final InputStream std1 = p.getInputStream();
				final InputStream std2 = p.getErrorStream();
				new Thread() {
					public void run() {
						cmdCallback.stderrCallback(defaultEncoding,std2);
					}
				}.start();

				new Thread() {
					public void run() {
						cmdCallback.stdinCallBack(defaultEncoding,std1);
					}
				}.start();
				p.waitFor();
				p.destroy();
			} catch (Exception e) {
				log.error(e);
				try {
					p.getErrorStream().close();
					p.getInputStream().close();
					p.getOutputStream().close();
				} catch (Exception ee) {
					log.error(ee);
				}

			}

		} else {
			p = rt.exec(new String[] { "/bin/bash", "-c", command }, null, null);
			p.waitFor();
			InputStreamReader isr = new InputStreamReader(p.getInputStream(), defaultEncoding);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				log.info(line);
			}
			isr.close();
			br.close();
		}
		p=null;
	}

}
