package crawler.httpRpcServer.process;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public interface HttpRpcCommandProcess {
	String execCmd(String type,String cmd,String... args) throws Exception;
}
