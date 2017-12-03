package light.mvc.utils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

public class HttpUtil {
	/**
	 * http发送
	 * 
	 * @author wq
	 * @date 2014-11-3
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public static String httpSendMessage(String json, String url) throws Exception {
		//System.out.println("requestJson 请求报文:" + json);
		////System.out.println("url 请求地址:" + url);
		String responseStr = "";
		HttpURLConnection conn = null;
		ByteArrayOutputStream buffer = null;
		BufferedInputStream bufIn = null;
		URL urlAddress = new URL(url);
		conn = (HttpURLConnection) urlAddress.openConnection();
		conn.setRequestMethod("POST");
		conn.setReadTimeout(300000);
		conn.setConnectTimeout(300000);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Length",
				String.valueOf(json.getBytes("UTF-8").length));
		conn.setRequestProperty("Content-Type", "text/json");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		OutputStream out = conn.getOutputStream();
		out.write(json.getBytes("UTF-8"));
		out.flush();
		out.close();
		
		bufIn = new BufferedInputStream(conn.getInputStream());
		buffer = new ByteArrayOutputStream();
		byte[] tmpBuf = new byte[1024];
		int len = bufIn.read(tmpBuf);
		while (len != -1) {
			buffer.write(tmpBuf, 0, len);
			len = bufIn.read(tmpBuf);
		}
		responseStr = new String(buffer.toByteArray(), "UTF-8");
		buffer.close();
		bufIn.close();
		
		conn.disconnect();
		System.out.println(JSONObject.fromObject(json).get("service")+"-响应报文:"+responseStr);
		return responseStr;
	}

	public static String httpsSendMessage(String json,String url) throws Exception {
		String result = null;

			// 设置SSLContext
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager },
					null);

			// 打开连接
			// 要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
			URL requestUrl = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) requestUrl
					.openConnection();
			ByteArrayOutputStream buffer = null;
			BufferedInputStream bufIn = null;
			// 设置套接工厂
			conn.setSSLSocketFactory(sslcontext.getSocketFactory());
			conn.setHostnameVerifier(new NullHostnameVerifier());
			
			//conn.setDefaultHostnameVerifier(new NullHostnameVerifier());
			
			conn.setRequestMethod("POST");
			conn.setReadTimeout(180000);
			conn.setConnectTimeout(180000);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(json.getBytes("UTF-8").length));
			conn.setRequestProperty("Content-Type", "text/json");
			OutputStream out = conn.getOutputStream();
			out.write(json.getBytes("UTF-8"));
			out.flush();
			out.close();
			bufIn = new BufferedInputStream(conn.getInputStream());
			buffer = new ByteArrayOutputStream();
			byte[] tmpBuf = new byte[1024];
			int len = bufIn.read(tmpBuf);
			while (len != -1) {
				buffer.write(tmpBuf, 0, len);
				len = bufIn.read(tmpBuf);
			}
			result = new String(buffer.toByteArray(), "UTF-8");
			buffer.close();
			bufIn.close();
			conn.disconnect();
			//System.out.println("responseJson 响应报文:" + result);

		

		return result;
	}

	private static TrustManager myX509TrustManager = new X509TrustManager() {

		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return certificates;
		}

		@Override
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			if (this.certificates == null) {  
	            this.certificates = arg0;  
	        }  
		}

		@Override
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			 if (this.certificates == null) {  
		            this.certificates = arg0;  
		        }  
		}
		
		private java.security.cert.X509Certificate[] certificates;  
		  
		
	};
	
	private static class NullHostnameVerifier implements HostnameVerifier {
	    

		@Override
		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}
	}
	
	
	
	
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url,String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print("param="+param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    

}
