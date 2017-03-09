package crawler.httpRpcServer.command;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author zengfr
 *  QQ:362505707/1163551688 
 * Email:zengfr3000@qq.com
 */
public class CommandHttpUtil {
	private static Log log = LogFactory.getLog(CommandHttpUtil.class);
	private static HttpClientBuilder httpClientBuilder=HttpClientBuilder.create();
	private static Map<String,String> excludeHeads=new HashMap<String,String>();
	static{
		String value=null;
		excludeHeads.put("Host", value);
	}
	public static void exec(String cmd, String[] arg, List<Entry<CharSequence, CharSequence>> heads, String content,
			CommandCallback cmdCallback) throws Exception {
		if (arg != null && arg.length > 0) {
			String url = arg[0];
			HttpClient httpclient = httpClientBuilder.build();
			HttpResponse response = null;

			switch (cmd) {
			case "get":
				HttpGet httpget = new HttpGet(url);
				for (Entry<CharSequence, CharSequence> namevalues : heads) {
					if(!excludeHeads.containsKey(namevalues.getKey().toString())){
						httpget.setHeader(namevalues.getKey().toString(), namevalues.getValue().toString());
						log.info(String.format("setHeader:%s",namevalues.getKey().toString()));
					}
				}
				response = httpclient.execute(httpget);httpget=null;
				break;
			case "post":
				HttpPost httpPost = new HttpPost(url);
				for (Entry<CharSequence, CharSequence> namevalues : heads) {
					if(!excludeHeads.containsKey(namevalues.getKey().toString())){
						httpPost.setHeader(namevalues.getKey().toString(), namevalues.getValue().toString());
						log.info(String.format("setHeader:%s",namevalues.getKey().toString()));
					}
				}
				HttpEntity entity = new StringEntity(content);
				httpPost.setEntity(entity);
				response = httpclient.execute(httpPost);httpPost=null;
				break;
			}
			HttpEntity entity = response.getEntity();
			// String result= EntityUtils.toString(entity);
			if (entity != null) {
				ContentType contentType = ContentType.getOrDefault(entity);
				Charset charSet = contentType.getCharset();
				String charSetName = charSet.name();
				log.info(String.format("charSet:%s,%s,%s",charSet,charSetName,charSet.displayName()));
				InputStream stream = entity.getContent();
				cmdCallback.stdinCallBack(charSetName,stream);
				entity=null;
			}
			response=null;
		}
	}
}
