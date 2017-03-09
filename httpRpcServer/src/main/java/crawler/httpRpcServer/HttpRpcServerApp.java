package crawler.httpRpcServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import crawler.httpRpcServer.process.HttpRpcCommandProcess;
import crawler.httpRpcServer.process.HttpRpcCommandProcessImpl;
import crawler.httpRpcServer.server.HttpRpcServer;

/**
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 */
public class HttpRpcServerApp {
	private static Log log = LogFactory.getLog(HttpRpcServerApp.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int port = 8844;

		String inetHost = "0.0.0.0";
		if (args != null) {
			if (args.length > 0)
				port = Integer.valueOf(args[0]);
			if (args.length > 1)
				inetHost = String.valueOf(args[1]);
		}
		startRpcServer(inetHost, port);
	}

	public static void startRpcServer(String inetHost, int port) throws Exception {
		HttpRpcServer server = new HttpRpcServer();
		log.info("HttpRpcServer listening on " +inetHost+":"+ port + "...");
		HttpRpcCommandProcess commandProcess = new HttpRpcCommandProcessImpl();
		server.start(commandProcess, inetHost, port);
	}
}
