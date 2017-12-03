package light.mvc.utils;

import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 微信退款
 * @author hanyi
 *
 */
public class ReFund {
	/**
	 * 发起退款
	 * @param out_refund_no退款单号
	 * @param out_trade_no订单号
	 * @param total_fee总金额
	 * @param refund_fee退款金额
	 */
	public static String  wechatRefund(String out_refund_no,String out_trade_no,String total_fee,String refund_fee,String mch_id,String appid,String appsecret,String cert) {
		//api地址：http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
		//String out_refund_no = "20160807034429193411";// 退款单号
		//String out_trade_no = "20160807034429193411";// 订单号
		//String total_fee = "1";// 总金额
		//String refund_fee = "1";// 退款金额
		
		String currTime = TenpayUtil.getCurrTime();
		//8位日期
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		String nonce_str = strReq;// 随机字符串
		//String appid = SystemParam.appid; //微信公众号apid
		//String appsecret = SystemParam.appsecret; //微信公众号appsecret
		//String mch_id = SystemParam.mch_id;  //微信商户id
		String op_user_id = mch_id;//就是MCHID
		String partnerkey = SystemParam.partnerkey;//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);

		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee + "</total_fee>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		//String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String s="";
		try {
			s= ClientCustomSSL.doRefund(createOrderURL, xml, cert,mch_id);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public static void main(String[] args) {
		System.out.println(SystemParam.certapp);
		System.out.println(wechatRefund("2017011200002745", "2017011200002745", "1", "1", "1369235902", "wxbdbdd13448d1376b", "d4a42a504dc9af5e1a71632eb9e3245d", "D:/cert/apiclient_cert.p12"));
	}
}
