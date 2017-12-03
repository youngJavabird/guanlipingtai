package light.mvc.controller.refund;


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
import light.mvc.pageModel.jtrefund.Jtrefund;
import light.mvc.pageModel.operate.Operate;
import light.mvc.pageModel.orderinfo.OrderinfoView;
import light.mvc.pageModel.refundrecord.Refundrecord;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.service.operate.OperateServiceI;
import light.mvc.service.refund.JtrefundServiceI;
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
@RequestMapping("/jtrefund")
public class JtrefundController extends BaseController {
	
	Logger logger = Logger.getLogger(JtrefundController.class.getName());
	private final String ENCODE = "UTF-8";
	private final String CONTENT_TYPE = "text/html;charset=UTF-8";

	@Autowired
	private JtrefundServiceI jtrefundService;


	@RequestMapping("/manager")
	public String manager() {
		return "/refund/jtrefund";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Jtrefund jtrefund, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(jtrefundService.dataGrid(jtrefund, ph));
		grid.setTotal(jtrefundService.count(jtrefund, ph));
		return grid;
	}
	
	/**
	 * 允许退款
	 * @param id
	 * @return
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Json check(HttpServletRequest request,HttpServletResponse response,String id,String remark,String refundexplain) {
		Json j = new Json();
		try {
			jtrefundService.check(id);
			Refundrecord refundrecord=new Refundrecord();
			refundrecord.setRefundid(id);
			refundrecord.setState("2");
			refundrecord.setRemark(refundexplain);
			jtrefundService.add(refundrecord);
			j.setMsg("操作成功！");
			j.setSuccess(true);		
		}  catch (Exception e) {
			logger.info("允许退款异常:"+ReqJsonUtil.errInfo(e));
			j.setMsg(e.getMessage());
		}
		

		return j;
	}
	
	/**
	 * 拒绝退款
	 * @param id
	 * @return
	 */
	@RequestMapping("/refusecheck")
	@ResponseBody
	public Json refusecheck(HttpServletRequest request,HttpServletResponse response,String id,String remark,String openid) {
		Json j = new Json();
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("transtype", "0075");//退款拒绝审核接口
			requestData.put("from_user_name", openid);
//			requestData.put("weorderid", orderid);
			requestData.put("state", '0');
			requestData.put("transdate",  ReqJsonUtil.gettransdate());
			logger.info("已退款拒绝审核接口请求报文:"+requestData.toString());
			String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
			logger.info("已退款拒绝审核接口返回报文:"+repjsonstr);
			JSONObject repjson = JSONObject.fromObject(repjsonstr);
			String resultcode=repjson.getString("retcode");
			if(resultcode.equals("00")){
			jtrefundService.refusecheck(id,remark);
			Refundrecord refundrecord=new Refundrecord();
			refundrecord.setRefundid(id);
			refundrecord.setState("3");
			refundrecord.setRemark(remark);
			jtrefundService.add(refundrecord);
			j.setMsg("操作成功！");
			j.setSuccess(true);	
		} else{
			j.setMsg(repjson.getString("result"));
			j.setSuccess(false);
		}
		}catch (Exception e) {
			logger.info("拒绝退款异常:"+ReqJsonUtil.errInfo(e));
			j.setMsg(e.getMessage());
		}
		

		return j;
	}
	
	/**
	 * 确认退款
	 * @param id
	 * @return
	 */
	@RequestMapping("/oncheck")
	@ResponseBody
	public Json oncheck(HttpServletRequest request,HttpServletResponse response,String id,String openid) {
		Json j = new Json();
		try {
			request.setCharacterEncoding(ENCODE);
			response.setContentType(CONTENT_TYPE);
			response.setCharacterEncoding(ENCODE);
			String result=ReqJsonUtil.reqJson("02", "调用失败"); 	
			JSONObject requestData = new JSONObject();
			requestData.put("transtype", "0080");//确认退款接口
			requestData.put("from_user_name", openid);
			requestData.put("transdate",  ReqJsonUtil.gettransdate());
			logger.info("账号退款接口请求报文:"+requestData.toString());
			String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
			logger.info("账号退款接口返回报文:"+repjsonstr);
			JSONObject repjson = JSONObject.fromObject(repjsonstr);
			String resultcode=repjson.getString("retcode");
			if(resultcode.equals("00")){
				jtrefundService.oncheck(id);
				Refundrecord refundrecord=new Refundrecord();
				refundrecord.setRefundid(id);
				refundrecord.setState("4");
//				refundrecord.setRemark(remark);
				jtrefundService.add(refundrecord);
				logger.info("账号退款申请成功");
				j.setMsg("操作成功！");
				j.setSuccess(true);
			}else{
				j.setMsg(repjson.getString("result"));
				j.setSuccess(false);
			}			
		}  catch (Exception e) {
			logger.info("确认退款异常:"+ReqJsonUtil.errInfo(e));
			j.setMsg(e.getMessage());
		}
		

		return j;
	}
	



}
