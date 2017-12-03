package light.mvc.utils;

import java.util.UUID;


import net.sf.json.JSONObject;



public class RequestApi {
	 public static String  api(JSONObject requestHead) throws Exception{

	
		// 签名
		String sign = StringUtils.signSHA512(requestHead.toString() + SystemParam.api_sha512key);
		requestHead.put("sign", sign);
			
		// 发送
	System.out.println(requestHead.toString());   //请求报文
		String result   = HttpUtil.sendPost(requestHead.toString(),SystemParam.api_url);
		JSONObject resultJson = JSONObject.fromObject(result);
						
		// 验签	
		sign = resultJson.getString("sign");
		resultJson.put("sign", "");
		boolean verifySign = StringUtils.verifySignSHA1(sign, resultJson.toString() + SystemParam.api_sha512key);
						  
	   return result;
	}
	 public static void main(String[] args) {
		 JSONObject requestHead = new JSONObject();
			requestHead.put("sign", "");
	    	requestHead.put("service", "app");
			JSONObject requestData = new JSONObject();
			requestData.put("transtype", "0038");//渠道备份金查询接口
			requestData.put("transdate",  ReqJsonUtil.gettransdate());
		//requestData.put("userid", "15021078394");
		//	requestData.put("token", "C5DC14645F0F96BAF766F2E815BCDE61C75184A1AD2F97D2189CF19B103FD759966887544C729D6F15764FA6A0575B41CA5A48C64670E5D5A64EE9F0AAEDE5F6E9316A8B56D0F9DE997AAA83FF0179B725F440019CA61F890052F30F5F22700C076093C4015CEA04435C305AA22A41AFB02840D700041D7B418D145FEB910C4FA6A4D85ED5C547A795CE9970EF692AC33ECB6186890DCC2F710383E1CE6903FAE104356204D72A68DB3F5F011DE7AA71D25CBBA5CF0803C969BF8E95862B8AF2434B950E216AB8A83D6C640577D6040711F5FD66902381F72C6CF105F8AB4A8E0582F515EE6694FE718FB64745B0F8400AEA86BADC8FCF2A01A3C5F4AF525ACE");
			requestData.put("channelid", "1");
			requestHead.put("requestdata", requestData);
			try {
				System.out.println(requestHead);
				String repjsonstr=	RequestApi.api(requestHead);
				System.out.println(repjsonstr);
				JSONObject repjson = JSONObject.fromObject(repjsonstr);
				String responsedata=repjson.getString("responsedata");
				JSONObject asd=JSONObject.fromObject(responsedata);
				String amount=asd.getString("amount");
	     		System.out.println(amount);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
