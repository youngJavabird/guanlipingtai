package light.mvc.utils;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.XML;






import net.sf.json.JSONObject;

public class ReqJsonUtil {
	/**
	 * 错误返回信息封装
	 * @param retcode
	 * @param result
	 * @return
	 */
	public static String reqJson(String retcode,String result){
		JSONObject resultJSON =new JSONObject();
		resultJSON.put("retcode", retcode);
		resultJSON.put("result", result);
		String resultStr=resultJSON.toString();
		return resultStr;
	}
	/**
	 * xml格式转换
	 * @param xml
	 * @return
	 */
	public static String getJsonforXML(String xml){
		//logger.info("xml:"+xml);
		// xml = "<root><name type='type'>zhaipuhong</name><gender>male</gender><birthday><year>1970</year><month>12</month><day>17</day></birthday></root>";
        String json = null;
        org.json.JSONObject jsonObj = null;
        try {
            jsonObj = XML.toJSONObject(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            json = jsonObj.toString();        //有缩进
        } catch (Exception e) {
            e.printStackTrace();
        }
       // System.out.println(json);
       // logger.info("json:"+json);
        return json;
	}
	/**
	 * 获取交易时间
	 * @return
	 */
	public static String gettransdate(){
		String transdate =FormatDate.getDate("yyyyMMdd");
        return transdate;
	}
	/**
	 * 打印堆栈信息
	 * @param e
	 * @return
	 */
	public static String errInfo(Exception e) {  
        StringWriter sw = null;  
        PrintWriter pw = null;  
        try {  
            sw = new StringWriter();  
            pw = new PrintWriter(sw);  
            // 将出错的栈信息输出到printWriter中  
            e.printStackTrace(pw);  
            pw.flush();  
            sw.flush();  
        } finally {  
            if (sw != null) {  
                try {  
                    sw.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (pw != null) {  
                pw.close();  
            }  
        }  
        return sw.toString();  
    }  
	public static void main(String[] args) {
		System.out.println(gettransdate());
		gettransdate();
	}
}
