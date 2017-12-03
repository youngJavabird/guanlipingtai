package light.mvc.utils;

import net.sf.json.JSONObject;


public class SocketUtil {

	public static String sendToCore(String phone,String message) {

		String result = null;
		
		JSONObject json = JSONObject.fromObject(message);
		json.put("sign", "");
		json.put("sign", MD5.GetMD5Code(json.toString()+phone+SystemParam.md5key));
		SSocketClientImpl ssocket = new SSocketClientImpl(SystemParam.cip,
				SystemParam.cport, 5000);
		try {

			result = ssocket.send(json.toString().getBytes("UTF-8"));
			
		} catch (Exception e) {

			return "{\"retcode\":\"99\",\"result\":\"异常\"}";
		}

		return result;
	}
	
	public static String sendToCore2(String message) {

		String result = null;
		
		JSONObject json = JSONObject.fromObject(message);
		json.put("sign", "");
		json.put("sign", MD5.GetMD5Code(json.toString()+SystemParam.md5key));
		System.out.println(json.toString());
		SSocketClientImpl ssocket = new SSocketClientImpl(SystemParam.cip,
				SystemParam.cport, 5000);
		try {

			result = ssocket.send(json.toString().getBytes("UTF-8"));
			
		} catch (Exception e) {

			return "{\"retcode\":\"99\",\"result\":\"异常\"}";
		}

		return result;
	}

}
