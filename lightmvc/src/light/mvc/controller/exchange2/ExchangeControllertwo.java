package light.mvc.controller.exchange2;


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
@RequestMapping("/exchangetwo")
public class ExchangeControllertwo extends BaseController {
	
	Logger logger = Logger.getLogger(ExchangeControllertwo.class.getName());
	private final String ENCODE = "UTF-8";
	private final String CONTENT_TYPE = "text/html;charset=UTF-8";

	@Autowired
	private ExchangeServiceTwoI exchangeServicetwo;
	
	@Autowired
	private OperateServiceI operateService;

	@RequestMapping("/manager")
	public String manager() {
		return "/exchange/exchangetwo";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Scoredetail scoredetail, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(exchangeServicetwo.dataGrid(scoredetail, ph));
		grid.setTotal(exchangeServicetwo.count(scoredetail, ph));
		return grid;
	}
	
	/**
	 * 审核
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
		Object ope=(Object)exchangeServicetwo.getopera2(userid, card_code,sendid).get(0).getOpera();
		if(String.valueOf(ope).equals(opera)){
			j.setMsg("申请人与审核相同，不能审核！");
			j.setSuccess(false);
		}else{
		JSONObject requestData = new JSONObject();
		requestData.put("transtype", "0071");//已退卡券价钱接口
		requestData.put("from_user_name", userid);
		requestData.put("guid", guid);
		requestData.put("cardtype", cardtype);
		requestData.put("orderid", orderid);
		requestData.put("transdate",  ReqJsonUtil.gettransdate());
		logger.info("已退卡券未加钱接口请求报文:"+requestData.toString());
		String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
		logger.info("已退卡券未加钱接口返回报文:"+repjsonstr);
		JSONObject repjson = JSONObject.fromObject(repjsonstr);
		String resultcode=repjson.getString("retcode");
		if(resultcode.equals("00")){
			j.setMsg("操作成功");
			j.setSuccess(true);	
			Operate operate=new Operate();
			operate.setUserid(userid);
			operate.setCard_code(card_code);
			operate.setPhone(phone);
			operate.setPrice(price);
			operate.setTypename(typename);
			operate.setOpera(opera);
			operate.setState("1");
			operate.setGuid(orderid);
			operate.setSendid(sendid);
			operateService.add(operate);
			logger.info(opera+"在"+ReqJsonUtil.gettransdate()+"通过了userid为"+userid+"卡券guid为"+guid+"的退券审核");
		}else{
			j.setMsg(repjson.getString("result"));
			j.setSuccess(false);
		}
		}
		}  catch (Exception e) {
			logger.info("已退卡券未加钱接口异常:"+ReqJsonUtil.errInfo(e));
			j.setMsg(e.getMessage());
		}
		

		return j;
	}
	
	@RequestMapping("/refusecheck")
	@ResponseBody
	public Json refusecheck(HttpServletRequest request,HttpServletResponse response,String userid,String guid,String cardtype,String state,String card_code,String typename,String phone,String orderid,String price,String opera,String oldstate,String sendid) {
		Json j = new Json();
		try {
			//传入传出字符编码
		request.setCharacterEncoding(ENCODE);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(ENCODE);
		String result=ReqJsonUtil.reqJson("02", "调用失败"); 	
		Object ope=(Object)exchangeServicetwo.getopera2(userid, card_code,sendid).get(0).getOpera();
		if(String.valueOf(ope).equals(opera)){
			j.setMsg("申请人与审核相同，不能审核！");
			j.setSuccess(false);
		}else{
		JSONObject requestData = new JSONObject();
		requestData.put("transtype", "0073");//已退卡券拒绝审核
		requestData.put("from_user_name", userid);
		requestData.put("guid", guid);
		requestData.put("cardtype", cardtype);
		requestData.put("orderid", orderid);
		requestData.put("oldstate", oldstate);
		requestData.put("transdate",  ReqJsonUtil.gettransdate());
		logger.info("已退卡券拒绝审核接口请求报文:"+requestData.toString());
		String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
		logger.info("已退卡券拒绝审核接口返回报文:"+repjsonstr);
		JSONObject repjson = JSONObject.fromObject(repjsonstr);
		String resultcode=repjson.getString("retcode");
		if(resultcode.equals("00")){
			j.setMsg("操作成功");
			j.setSuccess(true);
			Operate operate=new Operate();
			operate.setUserid(userid);
			operate.setCard_code(card_code);
			operate.setPhone(phone);
			operate.setPrice(price);
			operate.setTypename(typename);
			operate.setOpera(opera);
			operate.setState("2");
			operate.setGuid(orderid);
			operate.setSendid(sendid);
			operateService.add(operate);
			logger.info(opera+"在"+ReqJsonUtil.gettransdate()+"拒绝了userid为"+userid+"卡券guid为"+guid+"的退券审核");
		}else{
			j.setMsg("用户"+userid+repjson.getString("result"));
			j.setSuccess(false);
		}
		}
		}  catch (Exception e) {
			logger.info("已退卡券拒绝审核接口异常:"+ReqJsonUtil.errInfo(e));
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
