package light.mvc.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import light.mvc.pageModel.base.VinfoBean;
import light.mvc.pageModel.card.Card;

/**
 * 查询卡券状态
 * @author lianss
 *
 */
public class CardUtil {
	static Logger logger = Logger.getLogger(CardUtil.class.getName());
	
	public static String sendquery(List<Card> cardlist){
		
		JSONObject req = new JSONObject();
		req.put("service", "vinfo");
		req.put("formid", "000000000000001");
		req.put("signtype", "MD5");
		req.put("charset", "UTF-8");
		req.put("sign", "");
		
		JSONObject reqdata = new JSONObject();
		reqdata.put("useid", "sssssssss");
		reqdata.put("merchantno", SystemParam.merchno);
		reqdata.put("typeid", cardlist.get(0).getCard_typeid());
		reqdata.put("seqnoform", cardlist.get(0).getCard_seqno());
		reqdata.put("seqnoto", cardlist.get(cardlist.size()-1).getCard_seqno());
		reqdata.put("ispager", "N");
		reqdata.put("pageno", "");
		reqdata.put("pagesize", "");
	 
		req.put("requestdata", reqdata.toString());
		req.put("sign", MD5.GetMD5Code(req.toString()+SystemParam.md5key));
		logger.info("加密后 发送 报文："+req.toString()+SystemParam.md5key);
		String result = HttpUtil.sendPost(SystemParam.serviceurl,req.toString());
	
		logger.info("vinfo  返回报文："+result);
		return result; 
	 }


	 
	 /**
	  * 撤销
	  * @param card
	  * @return
	  */
	 public  static String islv(List<Card> card){
		 JSONObject requestjson = new JSONObject();
		 requestjson.put("service", "vidvv");
		 requestjson.put("formid", "000000000000001");
		 requestjson.put("signtype", "MD5");
		 requestjson.put("charset", "UTF-8");
		 requestjson.put("sign", "");
		 JSONObject requestdata = new JSONObject();
		 requestdata.put("userid", "sssss");
		 requestdata.put("voucherno", card.get(0).getCard_code());
		 requestdata.put("number", card.size());
		 double price = Double.parseDouble(card.get(0).getCard_price())/100;
		 requestdata.put("amount", price);
		 requestdata.put("totalamount", price*card.size());
		 requestdata.put("reqid", "fdsfjkdlfs");
		 requestdata.put("merchantno", SystemParam.merchno);
		 requestjson.put("requestdata", requestdata.toString());
		 requestjson.put("sign", MD5.GetMD5Code(requestjson.toString()+SystemParam.md5key));
		 logger.info("加密后 发送 报文："+requestjson.toString()+SystemParam.md5key);
		 String result = HttpUtil.sendPost(SystemParam.serviceurl,requestjson.toString());
		
		 logger.info("vidvv  返回报文："+result);
		return result; 
		 
	}
	 
	public static String send(List<Card> card, String getCardTypeID) throws Exception{
		String result = "{\"resultcode\":\"99\",\"errormsg\":\"无效参数\"}";
		
		//有两种可选参数组合，1：券类型，起始和终止序列号，2：券号，二选一
		JSONObject requestHead = new JSONObject();
		requestHead.put("service", "vislv");
		requestHead.put("formid", "000000000000876" );
		requestHead.put("signtype", "MD5");
		requestHead.put("charset", "UTF-8");
		requestHead.put("sign", "");
		JSONObject requestdata  =  new JSONObject();
		if(card.size()>1){
			logger.info("cardlist  seqno:"+card.get(0).getCard_seqno());
			requestdata.put("typeid", getCardTypeID);
			requestdata.put("seqnoform", card.get(0).getCard_seqno());
			requestdata.put("seqnoto", card.get(card.size()-1).getCard_seqno());
		}else{
			requestdata.put("voucherno", card.get(0).getCard_code());
		}
		requestdata.put("userid", card.get(0).getCard_userid());
		
		requestdata.put("merchantno", SystemParam.merchno);
		String price = getmoney(card.get(0).getCard_price());
		requestdata.put("amount", price);
		
		//先转换为bigdecimal类型，做乘法计算，算出总金额
		String total= (Double.parseDouble(price)*card.size())+"";
		logger.info("单券金额："+price+"  |  总金额："+total);
		//再将总金额转换为String类型发送激活交易
		requestdata.put("totalamount", total);
		requestdata.put("number", card.size());
		requestdata.put("ispager", "N");
		
		requestHead.put("requestdata", requestdata.toString());
		logger.info("加密前报文："+requestHead.toString()+"4E64B423C2E449A9B234C083256D97CA");
		requestHead.put("sign",MD5.GetMD5Code(requestHead.toString()+"4E64B423C2E449A9B234C083256D97CA"));
		logger.info("send to kaquan requestHead："+requestHead);
		logger.info("requestUrl:" + "http://"+ SystemParam.cardAPIUrl +"/cardcoupons/client/kq");
		result = HttpUtil.sendPost("http://"+ SystemParam.cardAPIUrl +"/cardcoupons/client/kq",requestHead.toString());//SystemParam.serviceurl

		logger.info("激活卡券返回报文："+result);
		
		//激活信息入库
		JSONObject json = JSONObject.fromObject(result);
		json.get("");
		return result;
	}
	
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";  
	public static String getmoney(String amount) throws Exception{    
        if(!amount.matches(CURRENCY_FEN_REGEX)) {    
            throw new Exception("金额格式有误");    
        }    
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();    
    }    

	/**
	 * 取查询结果中的status
	 * @param result
	 * @return
	 */
	public static String getStatus(String result){
		String status = "2";
	
		JSONObject json = JSONObject.fromObject(result);
		if(json.get("resultcode").equals("00")){
    		JSONObject respdata = json.getJSONObject("responsedata");

        	List<VinfoBean> vlist = new ArrayList<VinfoBean>();
        	if(StringUtil.isNotEmpty(respdata.getJSONArray("vouchers").get(0)+"")){
        		vlist = (List<VinfoBean>)JSONArray.toCollection(respdata.getJSONArray("vouchers"), VinfoBean.class);
            	for (VinfoBean v : vlist) {
            		status=v.getStatus();
				}
        	}
		}
		return status;
	}
}
