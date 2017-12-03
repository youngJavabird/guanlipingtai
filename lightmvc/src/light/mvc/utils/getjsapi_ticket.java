package light.mvc.utils;

import light.mvc.utils.SendMessageService.WebClientDevWrapper;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
public class getjsapi_ticket {
	
	/**
	 * 根据access_token获取jsapi_ticket
	 * @param token
	 * @return
	 */
	public static  String getjsapi_ticket(String token){
		String code="";
		
		String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
		HttpClient client = new DefaultHttpClient();
		client = WebClientDevWrapper.wrapClient(client);
		HttpPost post = new HttpPost(url);
		HttpEntity entity = null;
		try {
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = res.getEntity();
				String charset = EntityUtils.getContentCharSet(entity);
				if (charset == null) {
					charset = "utf-8";
				}
			}
			
			code = EntityUtils.toString(entity);
			Logger logger = Logger.getLogger(getjsapi_ticket.class.getName());
			logger.info("code:" + code);
			JSONObject objectParam=JSONObject.fromObject(code);
			code=(String) objectParam.get("ticket");
			//code = code.substring(code.indexOf(":") + 2, code.indexOf(",") - 1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return code;
	}
	public static void main(String[] args) {
		String token=getAccess_token.getToken();
		String ticket=getjsapi_ticket(token);
		System.out.println(ticket);
//		JSONObject objectParam=JSONObject.fromObject(ticket);
//		System.out.println(objectParam.get("ticket"));
//			   Date a = new Date();
//
//			   System.out.println(a.getTime()/1000);

	}
}
