/**   
 * This class is used for ...   
 * @author 龚小斌 
 * @version   
 *       1.0, 2014-7-22 下午04:51:25   
 */   

package light.mvc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import light.mvc.pageModel.orderinfo.PostBean;
import light.mvc.pageModel.orderinfo.TextMessage;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

public class PostUtil {
	public static String Post(TextMessage msg,PostBean pb) throws IOException {       
        URL url = new URL("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+pb.getToken()+"");     
        URLConnection connection = url.openConnection();    
        Map map=new HashMap();
        map.put("touser", pb.getTouser());
        map.put("msgtype", "text");
        map.put("text", msg);
        JSON json =JSONSerializer.toJSON(map);
        System.out.println(json);
        connection.setDoOutput(true);     
        /**   
         * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...   
         */    
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");     
        out.write(json.toString()); //向页面传递数据。post的关键所在！     
        // remember to clean up     
        out.flush();     
        out.close();     
        /**   
         * 这样就可以发送一个看起来象这样的POST：    
         * POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT:   
         * text/plain Content-type: application/x-www-form-urlencoded   
         * Content-length: 99 username=bob password=someword   
         */    
        // 一旦发送成功，用以下方法就可以得到服务器的回应：     
        String sCurrentLine;     
        String sTotalString;     
        sCurrentLine = "";     
        sTotalString = "";     
        InputStream l_urlStream;     
        l_urlStream = connection.getInputStream();      
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(     
                l_urlStream));     
        while ((sCurrentLine = l_reader.readLine()) != null) {     
            sTotalString += sCurrentLine;     
    
        }     
        return sTotalString;     
    }   
	public static void main(String[] args) throws Exception {
		 URL url = new URL("http://kele.vfengche.cn/klinzj/checkWechat.do");    	
		 URLConnection connection = url.openConnection();
		 connection.setDoOutput(true); 
	        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");  
	        out.write(" <xml><ToUserName><![CDATA[gh_05ccac232a6e]]></ToUserName>" +
	        		"<FromUserName><![CDATA[oZJO5uAz8ZU0nxL-8r9_S0_UD2dQ]]></FromUserName>" +
	        		" <CreateTime>1348831860</CreateTime>" +
	        		"<MsgType><![CDATA[text]]></MsgType>" +
	        		"<Content><![CDATA[喜士多]]></Content>" +
	        		"<MsgId>1234567890123456</MsgId></xml>"); //向页面传递数据。post的关键所在！     
	        // remember to clean up     
	        out.flush();     
	        out.close();    
	        String sCurrentLine;     
	        String sTotalString;     
	        sCurrentLine = "";     
	        sTotalString = "";     
	        InputStream l_urlStream;     
	        l_urlStream = connection.getInputStream();      
	        BufferedReader l_reader = new BufferedReader(new InputStreamReader(     
	                l_urlStream));     
	        while ((sCurrentLine = l_reader.readLine()) != null) {     
	            sTotalString += sCurrentLine;     
	    
	        }  
	        System.out.println(sTotalString);
		 }
         
}  

