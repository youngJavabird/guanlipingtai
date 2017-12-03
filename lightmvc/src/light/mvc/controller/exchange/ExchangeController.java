package light.mvc.controller.exchange;


import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import light.mvc.controller.activity.ActivityController;
import light.mvc.controller.base.BaseController;
import light.mvc.model.starwin.TStarWin;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.operate.Operate;
import light.mvc.pageModel.orderinfo.OrderinfoView;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.service.exchange.ExchangeServiceI;
import light.mvc.service.exchange.ExchangeServiceTwoI;
import light.mvc.service.operate.OperateServiceI;
import light.mvc.service.scoredetail.ScoredetailServiceI;
import light.mvc.service.starwin.WnfxhaccountServiceI;
import light.mvc.utils.HttpUtil;
import light.mvc.utils.MD5;
import light.mvc.utils.ReqJsonUtil;
import light.mvc.utils.SocketUtil;
import light.mvc.utils.SystemParam;
import light.mvc.utils.UploadUtil;


@Controller
@RequestMapping("/exchange")
public class ExchangeController extends BaseController {
	
	Logger logger = Logger.getLogger(ExchangeController.class.getName());
	private final String ENCODE = "UTF-8";
	private final String CONTENT_TYPE = "text/html;charset=UTF-8";

	@Autowired
	private ExchangeServiceI exchangeService;

	@Autowired
	private OperateServiceI operateService;

	@RequestMapping("/manager")
	public String manager() {
		return "/exchange/exchange";
	}

	@RequestMapping("/subcheck")
	public String subcheck(HttpServletRequest request, Long id) {
//		Product product = exchangeService.get(id);
//		request.setAttribute("product", product);
		return "/exchange/subcheck";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Scoredetail scoredetail, PageFilter ph) {
		Grid grid = new Grid();
		try {
		
			grid.setRows(exchangeService.dataGrid(scoredetail, ph));
			grid.setTotal(exchangeService.count(scoredetail, ph));
	}  catch (Exception e) {
		logger.info("卡券退券接口异常:"+ReqJsonUtil.errInfo(e));
	}

		return grid;
	}
	
	
	/**
	 * 提交审核
	 * @param id
	 * @return
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Json check(HttpServletRequest request,HttpServletResponse response,String userid,String guid,String cardtype,String state,String card_code,String typename,String phone,String orderid,String price,String opera,String sendid) {
		Json j = new Json();
		try {
			//传入传出字符编码
		request.setCharacterEncoding(ENCODE);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(ENCODE);
		String result=ReqJsonUtil.reqJson("02", "调用失败"); 	
		JSONObject requestData = new JSONObject();
		requestData.put("transtype", "0070");//增加活跃度接口
		requestData.put("from_user_name", userid);
		requestData.put("guid", guid);
		requestData.put("cardtype", cardtype);
		requestData.put("orderid", orderid);
		if(state.equals("未使用")){
			requestData.put("state", "0");
		}else if(state.equals("预备卡券")){
			requestData.put("state", "8");
		}else if(state.equals("未激活")){
			requestData.put("state", "9");
		}else{
			requestData.put("state", "");
		}
		requestData.put("transdate",  ReqJsonUtil.gettransdate());
		logger.info("卡券退券接口请求报文:"+requestData.toString());
		String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
		logger.info("卡券退券接口返回报文:"+repjsonstr);
		JSONObject repjson = JSONObject.fromObject(repjsonstr);
		String resultcode=repjson.getString("retcode");
		if(resultcode.equals("00")){
			logger.info("卡券退券申请成功");
			j.setMsg("操作成功！");
			j.setSuccess(true);
			Operate operate=new Operate();
			operate.setUserid(userid);
			operate.setCard_code(card_code);
			operate.setPhone(phone);
			operate.setPrice(price);
			operate.setTypename(typename);
			operate.setOpera(opera);
			operate.setState("0");
			operate.setGuid(orderid);
			operate.setSendid(sendid);
			operateService.add(operate);
		}else{
			j.setMsg("未知错误");
		}
		}  catch (Exception e) {
			logger.info("卡券退券接口异常:"+ReqJsonUtil.errInfo(e));
			j.setMsg(e.getMessage());
		}
		

		return j;
	}
	
	

		public static void main(String[] args) {
			JSONObject requestData = new JSONObject();
			requestData.put("transtype", "0070");//增加活跃度接口
			requestData.put("phone", "18217013341");
			requestData.put("guid", "");
			requestData.put("cardtype", "1");
			requestData.put("state", "9");
			requestData.put("transdate",  ReqJsonUtil.gettransdate());

			String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
                   System.out.println(requestData.toString());
			JSONObject repjson = JSONObject.fromObject(repjsonstr);
			System.out.println(repjsonstr);
		}

}
