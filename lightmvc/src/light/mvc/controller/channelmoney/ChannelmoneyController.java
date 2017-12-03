package light.mvc.controller.channelmoney;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




import light.mvc.controller.activity.ActivityController;
import light.mvc.controller.base.BaseController;
import light.mvc.utils.ReqJsonUtil;
import light.mvc.utils.RequestApi;


@Controller
@RequestMapping("/channelmoney")
public class ChannelmoneyController extends BaseController{
	
	Logger logger = Logger.getLogger(ActivityController.class.getName());

	private final String ENCODE = "UTF-8";
	private final String CONTENT_TYPE = "text/html;charset=UTF-8";
	
	@RequestMapping("/manager")
	public String manager() {
		return "/channelmoney/channelmoney";
	}
	
	@RequestMapping("/check")
	public String check(HttpServletRequest request,HttpServletResponse response) {
		try {
			//传入传出字符编码
		request.setCharacterEncoding(ENCODE);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(ENCODE);
		String result=ReqJsonUtil.reqJson("02", "调用失败"); 	
		JSONObject requestHead = new JSONObject();
		requestHead.put("sign", "");
		requestHead.put("service", "app");
		JSONObject requestData = new JSONObject();
		requestData.put("transtype", "0038");//渠道备份金查询接口
		requestData.put("channelid", "1");
		requestData.put("transdate",  ReqJsonUtil.gettransdate());
		requestHead.put("requestdata", requestData);
		logger.info("渠道备份金查询接口请求报文:"+requestHead.toString());
		String repjsonstr=	RequestApi.api(requestHead);
		logger.info("渠道备份金查询接口返回报文:"+repjsonstr);
		JSONObject repjson = JSONObject.fromObject(repjsonstr);
		String resultcode=repjson.getString("resultcode");
		if(resultcode.equals("00")){
			logger.info("渠道备份金查询成功");
			String responsedata=repjson.getString("responsedata");
			JSONObject asd=JSONObject.fromObject(responsedata);
			String amount=asd.getString("amount");
	     request.setAttribute("amount", amount);
			return "/channelmoney/success";
		}else{			
			String errormsg=repjson.getString("errormsg");
			result=ReqJsonUtil.reqJson(resultcode, errormsg);
			logger.info("渠道备份金查询失败,错误信息为:"+result);
		
			return "/channelmoney/fail";
		}
		}  catch (Exception e) {
			logger.info("渠道备份金查询业务异常:"+ReqJsonUtil.errInfo(e));
		}
		return "/channelmoney/fail";
}
}