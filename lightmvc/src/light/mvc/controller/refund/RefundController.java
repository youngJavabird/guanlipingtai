package light.mvc.controller.refund;


import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
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
import light.mvc.pageModel.demo.Demo;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.operate.Operate;
import light.mvc.pageModel.orderinfo.Orderinfo;
import light.mvc.pageModel.orderinfo.OrderinfoView;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.service.operate.OperateServiceI;
import light.mvc.service.orderinfo.OrderInfoServiceI;
import light.mvc.service.refund.RefundServiceI;
import light.mvc.service.scoredetail.ScoredetailServiceI;
import light.mvc.service.starwin.WnfxhaccountServiceI;
import light.mvc.utils.HttpUtil;
import light.mvc.utils.MD5;
import light.mvc.utils.ReqJsonUtil;
import light.mvc.utils.SocketUtil;
import light.mvc.utils.SystemParam;
import light.mvc.utils.UploadUtil;
/**
 * 退款
 * @author hanyi
 *
 */

@Controller
@RequestMapping("/refund")
public class RefundController extends BaseController {
	
	Logger logger = Logger.getLogger(RefundController.class.getName());
	private final String ENCODE = "UTF-8";
	private final String CONTENT_TYPE = "text/html;charset=UTF-8";

	@Autowired
	private RefundServiceI refundService;
	
	@Autowired
	private OrderInfoServiceI orderInfoService;

	@Autowired
	private OperateServiceI operateService;
	@RequestMapping("/manager")
	public String manager() {
		return "/refund/refund";
	}
	@RequestMapping("/editPage")
	public String viewPage(HttpServletRequest request, String orderid) {
		Scoredetail r = refundService.scoredetail(orderid);
		request.setAttribute("scoredetail", r);
		return "/refund/refundEdit";
	}
	@RequestMapping("/subcheck")
	public String subcheck(HttpServletRequest request, Long id) {
//		Product product = refundService.get(id);
//		request.setAttribute("product", product);
		return "/refund/subcheck";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Scoredetail scoredetail, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(refundService.dataGrid(scoredetail, ph));
		grid.setTotal(refundService.count(scoredetail, ph));
		return grid;
	}
	
	/**
	 * 提交审核
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Json check(HttpServletRequest request,HttpServletResponse response,Scoredetail scoredetail) throws UnsupportedEncodingException {
		Json j = new Json();
		//try {
			//传入传出字符编码
		request.setCharacterEncoding(ENCODE);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(ENCODE);
		String result=ReqJsonUtil.reqJson("02", "调用失败"); 	
		JSONObject requestData = new JSONObject();
		requestData.put("transtype", "0072");//账号退款接口
		requestData.put("from_user_name", scoredetail.getUserid());
		requestData.put("weorderid", scoredetail.getOrderid());
		requestData.put("money", num(scoredetail.getRemoney()));
		requestData.put("rebate", num(scoredetail.getPbalance()));
		requestData.put("fee", num(scoredetail.getRefee()));
		requestData.put("remark", scoredetail.getRemark());
		requestData.put("transdate",  ReqJsonUtil.gettransdate());
		logger.info("账号退款接口请求报文:"+requestData.toString());
		String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
		logger.info("账号退款接口返回报文:"+repjsonstr);
		JSONObject repjson = JSONObject.fromObject(repjsonstr);
		String resultcode=repjson.getString("retcode");
		if(resultcode.equals("00")){
			logger.info("账号退款申请成功");
			j.setMsg("操作成功！");
			j.setSuccess(true);

			Operate operate=new Operate();
			operate.setUserid(scoredetail.getUserid());
			operate.setGuid(scoredetail.getOrderid());
			operate.setOpera(scoredetail.getOpera());
			operate.setPhone(scoredetail.getPhone());
			operate.setPrice(scoredetail.getBalance());
			operate.setP_price(scoredetail.getPbalance());
			operate.setUpdate(scoredetail.getCreatedate());
			operate.setRemoney(num(scoredetail.getRemoney()));
			operate.setRefee(num(scoredetail.getRefee()));
			operate.setRemark(scoredetail.getRemark());
			operate.setState("3");
			operateService.add(operate);
			orderInfoService.EditRemark(scoredetail.getOrderid(), num(scoredetail.getRemoney()), num(scoredetail.getRefee()), scoredetail.getRemark());			
		}else{
			j.setMsg("系统异常，请联系管理员");
		}

		return j;
	}
	
	/**
	 * 提交审核
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/check2")
	@ResponseBody
	public Json check2(HttpServletRequest request,HttpServletResponse response,String userid,String orderid,String phone,String balance,String pbalance,String createdate,String opera) throws UnsupportedEncodingException {
		Json j = new Json();
		try {
			//传入传出字符编码
		request.setCharacterEncoding(ENCODE);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(ENCODE);
		String result=ReqJsonUtil.reqJson("02", "调用失败"); 	
		JSONObject requestData = new JSONObject();
		requestData.put("transtype", "0072");//账号退款接口
		requestData.put("from_user_name",userid);
		requestData.put("weorderid", orderid);
		requestData.put("money", num(balance));
		requestData.put("rebate", "0");
		requestData.put("fee", "0");
		requestData.put("transdate",  ReqJsonUtil.gettransdate());
		logger.info("账号退款接口请求报文:"+requestData.toString());
		String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
		logger.info("账号退款接口返回报文:"+repjsonstr);
		JSONObject repjson = JSONObject.fromObject(repjsonstr);
		String resultcode=repjson.getString("retcode");
		if(resultcode.equals("00")){
			logger.info("账号退款申请成功");
			j.setMsg("操作成功！");
			j.setSuccess(true);

			Operate operate=new Operate();
			operate.setUserid(userid);
			operate.setGuid(orderid);
			operate.setOpera(opera);
			operate.setPhone(phone);
			operate.setPrice(balance);
			operate.setP_price(pbalance);
			operate.setUpdate(createdate);
//			operate.setRemoney(num(scoredetail.getRemoney()));
//			operate.setRefee(num(scoredetail.getRefee()));
//			operate.setRemark(scoredetail.getRemark());
			operate.setState("3");
			operateService.add(operate);		
			orderInfoService.EditRemark(orderid, num(balance), "0", "");
		}else{
			j.setSuccess(false);
			j.setMsg(repjson.getString("result"));
		}
		
	}  catch (Exception e) {
		logger.info("账号退款接口接口异常:"+ReqJsonUtil.errInfo(e));
		j.setMsg(e.getMessage());
	}

		return j;
	}
	
	

		public static void main(String[] args) {
//			JSONObject requestData = new JSONObject();
//			requestData.put("transtype", "0070");//增加活跃度接口
//			requestData.put("phone", "18217013341");
//			requestData.put("guid", "");
//			requestData.put("cardtype", "1");
//			requestData.put("state", "9");
//			requestData.put("transdate",  ReqJsonUtil.gettransdate());
//
//			String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
//                   System.out.println(requestData.toString());
//			JSONObject repjson = JSONObject.fromObject(repjsonstr);
	
			System.out.println(num("52.30"));

		}
		
		public static String num(String s){
			
		  double a=Double.parseDouble(s)*100;
		  String b= String.valueOf(a);
	      String c = b.substring(0,b.indexOf("."));
		  return c;					
		}

}
