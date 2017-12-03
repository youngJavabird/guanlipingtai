package light.mvc.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
@SuppressWarnings("all")
public class SendMessageService {

	/**
	 * 获取ACCESS_TOKEN
	 * @param appid
	 * @param AppSecret
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String getCode(String appid, String AppSecret)
			throws ParseException, IOException {
		String code = "";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appid + "&secret=" + AppSecret + "";
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		code = EntityUtils.toString(entity);
		code = code.substring(code.indexOf(":") + 2, code.indexOf(",") - 1);
		return code;
	}
	/**
	 * https
	 * @author Administrator
	 *
	 */
	public static class WebClientDevWrapper {  
        public static HttpClient wrapClient(HttpClient base) {  
            try {  
                SSLContext ctx = SSLContext.getInstance("TLS");  
                X509TrustManager tm = new X509TrustManager() {  
                    public X509Certificate[] getAcceptedIssuers() {  
                        return null;  
                    }  
                    public void checkClientTrusted(  
                            java.security.cert.X509Certificate[] chain,  
                            String authType)  
                            throws java.security.cert.CertificateException {  
                    }  
                    public void checkServerTrusted(  
                            java.security.cert.X509Certificate[] chain,  
                            String authType)  
                            throws java.security.cert.CertificateException {  
                    }  
                };  
                ctx.init(null, new TrustManager[] { tm }, null);  
                SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
                ClientConnectionManager ccm = base.getConnectionManager();  
                SchemeRegistry sr = ccm.getSchemeRegistry();  
                sr.register(new Scheme("https", 443, ssf));  
                return new DefaultHttpClient(ccm, base.getParams());  
            } catch (Exception ex) {  
                ex.printStackTrace();  
                return null;  
            }  
        }  
    }
	
	/*
	 * SendTextMessage
	 * */
	@SuppressWarnings("deprecation")
	public static void SendMessage(String str,String code) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+code+"";

		HttpClient client = new DefaultHttpClient();  
        client = WebClientDevWrapper.wrapClient(client);  
        HttpPost post = new HttpPost(url);
        HttpEntity entity = null;
        try {  
        	StringEntity stringEntity = new StringEntity(new String(str.getBytes("utf-8"),"iso-8859-1"));
        	post.setEntity(stringEntity); 
        	HttpResponse res = client.execute(post);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                entity = res.getEntity();  
                String charset = EntityUtils.getContentCharSet(entity);  
                if(charset == null){  
                    charset = "utf-8";  
                }  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
        try {
			code = EntityUtils.toString(entity);
			System.out.println(str);
			System.out.println(code);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * SendImageMessage
	 * */
	@SuppressWarnings("deprecation")
	public static UploadResult uploadMadiaFile(String code,String filePath,String type) {
		UploadResult result = null;
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+code+"&type="+type;
        try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);   
			MultipartEntity reqEntity = new MultipartEntity();
			/** file param name */  
			FileBody bin = new FileBody(new File(filePath),"UTF-8","UTF-8");   
			reqEntity.addPart("media", bin); 
			httpPost.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(httpPost);   
			HttpEntity responseEntity = response.getEntity();     
			ObjectMapper mapper = new ObjectMapper();
			if (responseEntity != null){
				String str = inputStream2String(responseEntity.getContent());
			    result = mapper.readValue(str, UploadResult.class);   
			}   
			httpClient.getConnectionManager().shutdown();
			responseEntity.getContent().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 处理返回内容
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) throws IOException {   
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   
        int i = -1;   
        while ((i = is.read()) != -1) {   
            baos.write(i);   
        }   
        return baos.toString();   
    }
	
	
	//@Test
//	public void test1(){
//		String appid = "wx4db08ef51a364f40";
//		String AppSecret = "8f52ce8b5418485d1ae21358362c0956";
//		SendTextMessage sendMessage = new SendTextMessage();
//		sendMessage.setTouser("orB7rjp0vrNDXvNf2UeQpZ0yWb98");
//		sendMessage.setMsgtype("text");
//		Text text = new Text();
//		String content = "SZ,Big SB";
//		text.setContent(content);
//		sendMessage.setText(text);
//		
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			String code = getCode(appid, AppSecret);
//			System.out.println(code);
//			String str = mapper.writeValueAsString(sendMessage);
//			SendMessage(str, code);
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	@Test
	public void test2(){
		String appid="wxcd984bc926596f56";
		String AppSecret="73786409a65af2d27ebfb16a0d45d28a";
		String code;
		try {
			code = getCode(appid, AppSecret);
			String filePath = "F:\\coca-cola\\.metadata\\.me_tcat\\webapps\\klinzj\\wei\\2.jpg";
			UploadResult result = uploadMadiaFile(code, filePath,"image");
//			
//			SendImageMessage sendMessage = new SendImageMessage();
//			sendMessage.setTouser("");
//			sendMessage.setMsgtype("image");
//			Image image = new Image();
//			image.setMedia_id(result.getMedia_id());
//			sendMessage.setImage(image);
//			ObjectMapper mapper = new ObjectMapper();
//			String str = mapper.writeValueAsString(sendMessage);
// 		SendMessage(str, code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	@Test
//	public void test3(){
//		String appid = "wx4db08ef51a364f40";
//		String AppSecret = "8f52ce8b5418485d1ae21358362c0956";
//		String code;
//		try {
//			code = getCode(appid, AppSecret);
//			System.out.println(code);
//			String filePath = "D:\\test\\11.AMR";
//			UploadResult result = uploadMadiaFile(code, filePath,"voice");
//			System.out.println(result.getMedia_id());
//			
//			SendVoiceMessage sendMessage = new SendVoiceMessage();
//			sendMessage.setTouser("orB7rjjk0vZaIy_no6prwshagbQI");
//			sendMessage.setMsgtype("voice");
//			Voice voice = new Voice();
//			voice.setMedia_id(result.getMedia_id());
//			sendMessage.setVoice(voice);
//			
//			ObjectMapper mapper = new ObjectMapper();
//			String str = mapper.writeValueAsString(sendMessage);
//			
//			SendMessage(str, code);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void test4(){
//		String appid = "wx4db08ef51a364f40";
//		String AppSecret = "8f52ce8b5418485d1ae21358362c0956";
//		String code;
//		try {
//			code = getCode(appid, AppSecret);
//			System.out.println(code);
//			String filePath = "D:\\test\\22.mp4";
//			UploadResult result = uploadMadiaFile(code, filePath,"video");
//			System.out.println(result.getMedia_id());
//			
//			SendVideoMessage sendMessage = new SendVideoMessage();
//			sendMessage.setTouser("orB7rjjk0vZaIy_no6prwshagbQI");
//			sendMessage.setMsgtype("video");
//			Video video  = new Video();
//			video.setMedia_id(result.getMedia_id());
//			video.setTitle("点多");
//			video.setDescription("sss多多");
//			sendMessage.setVideo(video);
//			
//			ObjectMapper mapper = new ObjectMapper();
//			String str = mapper.writeValueAsString(sendMessage);
//			
//			SendMessage(str, code);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void test5(){
//		String appid = "wx4db08ef51a364f40";
//		String AppSecret = "8f52ce8b5418485d1ae21358362c0956";
//		String code;
//		try {
//			//http://music.qq.com/qqmusic.html?id=5046527
//			code = getCode(appid, AppSecret);
//			System.out.println(code);
//			String filePath = "D:\\test\\11.jpg";
//			UploadResult result = uploadMadiaFile(code, filePath,"image");
//			System.out.println(result.getMedia_id());
//			
//			SendMusicMessage sendMessage = new SendMusicMessage();
//			sendMessage.setTouser("orB7rjp0vrNDXvNf2UeQpZ0yWb98");
//			sendMessage.setMsgtype("music");
//			Music music  = new Music();
//			music.setTitle("把命都给你了");
//			music.setDescription("把命都给你了(电视剧《咱们结婚吧》片头曲)");
//			music.setMusicUrl("http://music.qq.com/qqmusic.html?id=5046527");
//			music.setHqmusicurl("http://music.qq.com/qqmusic.html?id=5046527");
//			music.setThumb_media_id(result.getMedia_id());
//			sendMessage.setMusic(music);
//			
//			ObjectMapper mapper = new ObjectMapper();
//			String str = mapper.writeValueAsString(sendMessage);
//			
//			SendMessage(str, code);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	public void test6(){
//		String appid = "wx4db08ef51a364f40";
//		String AppSecret = "8f52ce8b5418485d1ae21358362c0956";
//		String code;
//		try {
//			//http://music.qq.com/qqmusic.html?id=5046527
//			code = getCode(appid, AppSecret);
//			System.out.println(code);
//			
//			SendNewsMessage sendMessage = new SendNewsMessage();
//			sendMessage.setTouser("orB7rjjk0vZaIy_no6prwshagbQI");
//			sendMessage.setMsgtype("news");
//			News news = new News();
//			List<Articles> articles = new ArrayList<Articles>();
//			Articles item = new Articles();
//			item.setTitle("呵呵");
//			item.setPicurl("http://demo.3g-dm.com/demo/shjg/images/1-8.jpg");
//			item.setUrl("http://www.baidu.com");
//			item.setDescription("呵呵呵呵");
//			
//			Articles item1 = new Articles();
//			item1.setTitle("哈哈");
//			item1.setPicurl("http://demo.3g-dm.com/demo/shjg/images/right.png");
//			item1.setUrl("http://www.baidu.com");
//			item1.setDescription("哈哈哈");
//			articles.add(item);
//			articles.add(item1);
//			news.setArticles(articles);
//			sendMessage.setNews(news);
//			
//			ObjectMapper mapper = new ObjectMapper();
//			String str = mapper.writeValueAsString(sendMessage);
//			
//			SendMessage(str, code);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}