package crawler.httpRpcServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import crawler.httpRpcServer.process.HttpRpcCommandProcess;
import crawler.httpRpcServer.process.HttpRpcCommandProcessImpl;
import crawler.httpRpcServer.server.HttpRpcServer;
/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class HttpRpcServerApp 
{
	private static Log log = LogFactory.getLog(HttpRpcServerApp.class);
    /**
     * @param args
     * @throws Exception
     */
    public static void main( String[] args ) throws Exception
    {
    	int port=8844;
    	if(args!=null&&args.length>0)
    		port=Integer.valueOf(args[0]);
    	
		 startRpcServer(port);
    }
    public static void startRpcServer(int port) throws Exception {
        HttpRpcServer server = new HttpRpcServer();
        log.info("HttpRpcServer listening on port:"+port+"...");
        HttpRpcCommandProcess commandProcess=new HttpRpcCommandProcessImpl();
        server.start(commandProcess,port);
    }
}
