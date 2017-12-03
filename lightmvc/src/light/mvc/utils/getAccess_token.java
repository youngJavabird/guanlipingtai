package light.mvc.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import light.mvc.utils.SendMessageService.WebClientDevWrapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class getAccess_token {
	public static  String getToken(){
		String code="";
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+SystemParam.appid+"&secret="+SystemParam.appsecret+"";
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
			code = code.substring(code.indexOf(":") + 2, code.indexOf(",") - 1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return code;
	}
	public static void main(String[] args) {
		
		System.out.println("token="+getToken());
	}
}
