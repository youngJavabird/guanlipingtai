package light.mvc.controller.scoredetail;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import light.mvc.controller.base.BaseController;
import light.mvc.controller.exchange.ExchangeController;
import light.mvc.model.starwin.TStarWin;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.operate.Operate;
import light.mvc.pageModel.orderinfo.OrderinfoView;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.service.scoredetail.ScoredetailServiceI;
import light.mvc.service.starwin.WnfxhaccountServiceI;
import light.mvc.utils.ReqJsonUtil;
import light.mvc.utils.SocketUtil;
import light.mvc.utils.SystemParam;
import light.mvc.utils.UploadUtil;


@Controller
@RequestMapping("/scoredetail")
public class ScoredetailController extends BaseController {
	
	Logger logger = Logger.getLogger(ScoredetailController.class.getName());
	private final String ENCODE = "UTF-8";
	private final String CONTENT_TYPE = "text/html;charset=UTF-8";

	Scoredetail o_scoredetail=null;
	@Autowired
	private ScoredetailServiceI scoredetailServiceI;

	@RequestMapping("/manager")
	public String manager() {
		return "/scoredetail/scoredetail";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Scoredetail scoredetail, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(scoredetailServiceI.dataGrid(scoredetail, ph));
		grid.setTotal(scoredetailServiceI.count(scoredetail, ph));
		o_scoredetail =scoredetail;
		return grid;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/exportaccountinfo")
	private String ExportOrderInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		PageFilter ph = new PageFilter();
		ph.setRows(99999999);
		List<Scoredetail> list = scoredetailServiceI.dataGrid(o_scoredetail, ph);
		if (list.size()>65000) {
			String sendmessage="数据量过大无法导出";
			request.setAttribute("sendmessage", sendmessage);
			return "/scoredetail/scoredetail";
		}
		
		if (o_scoredetail == null||o_scoredetail.equals(null)) {
			String sendmessage="无导出数据";
			request.setAttribute("sendmessage", sendmessage);
			return "/scoredetail/scoredetail";
		}
		else{
			HSSFWorkbook wb = new HSSFWorkbook();
			//声明一个单子并命名
			HSSFSheet sheet = wb.createSheet("卡券交易信息表");
			//给单子名称一个长度
			sheet.setColumnWidth(0,(int)((3 + 0.72) * 256));
			sheet.setColumnWidth(1,(int)((33 + 0.72) * 256));
			sheet.setColumnWidth(2,(int)((20 + 0.72) * 256));
			sheet.setColumnWidth(3,(int)((20 + 0.72) * 256));
			sheet.setColumnWidth(4,(int)((10 + 0.72) * 256));
			sheet.setColumnWidth(8,(int)((20 + 0.72) * 256));
			sheet.setColumnWidth(13,(int)((20 + 0.72) * 256));
			sheet.setColumnWidth(15,(int)((20 + 0.72) * 256));

			// 生成一个样式  
			HSSFCellStyle style = wb.createCellStyle();
			HSSFCellStyle style2 = wb.createCellStyle();
			//创建第一行（也可以称为表头）
			HSSFRow row = sheet.createRow(0);
			//创建第二行
			HSSFRow row1 = sheet.createRow(1);
	
			//样式字体居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			//给表头第一行一次创建单元格
			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue("卡券交易信息详情");
			cell1.setCellStyle(style);
	
			//给表头第二行一次创建单元格
			HSSFCell cell2 = row1.createCell((short) 0);
			cell2.setCellValue("编号");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 1);  
			cell2.setCellValue("openid(微信用户ID)");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 2);  
			cell2.setCellValue("交易订单号");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 3);  
			cell2.setCellValue("交易时间");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 4);  
			cell2.setCellValue("交易场景");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 5);  
			cell2.setCellValue("交易类型");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 6);  
			cell2.setCellValue("金额(元)");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 7);  
			cell2.setCellValue("返现(元)");
			cell2.setCellStyle(style);			
			cell2 = row1.createCell( (short) 8);  
			cell2.setCellValue("券名");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 9);  
			cell2.setCellValue("市场价");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 10);  
			cell2.setCellValue("折后价");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 11);  
			cell2.setCellValue("采购价");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 12);  
			cell2.setCellValue("数量");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 13);  
			cell2.setCellValue("卡券状态更新时间");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 14);  
			cell2.setCellValue("卡券状态");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 15);  
			cell2.setCellValue("备注");
			cell2.setCellStyle(style);
			 sheet.addMergedRegion(new Region(0,(short)0,0,(short)15)); 
			 
//			 DecimalFormat    df   = new DecimalFormat("######0.00");   
//			  Double dd = Double.parseDouble(df.format(d));
			//向单元格里填充数据
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 2);
				row.createCell(0).setCellValue(i+1);
				row.createCell(1).setCellValue(list.get(i).getUserid());
				row.createCell(2).setCellValue(list.get(i).getOrderid());
				row.createCell(3).setCellValue(list.get(i).getCreatedate().toString());
				row.createCell(4).setCellValue(list.get(i).getPaytype());
				row.createCell(5).setCellValue(list.get(i).getType());			
				row.createCell(6).setCellValue(list.get(i).getBalance());
				row.createCell(7).setCellValue(list.get(i).getPbalance());
				row.createCell(8).setCellValue(list.get(i).getTypename());
				row.createCell(9).setCellValue(list.get(i).getP_price());
				row.createCell(10).setCellValue(list.get(i).getPrice());
				row.createCell(11).setCellValue(list.get(i).getPurchase());
				row.createCell(12).setCellValue(list.get(i).getPro_num());
				row.createCell(13).setCellValue(list.get(i).getUpdate().toString());
				row.createCell(14).setCellValue(list.get(i).getState());
				row.createCell(15).setCellValue(list.get(i).getRemark());
			}
			
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = df.format(new Date());
				System.out.println(System.getProperty("upload") );
				String path = SystemParam.uploadpath +date+".xls";
				FileOutputStream out = new FileOutputStream(path);
				wb.write(out);
				out.close();
				UploadUtil.download(path, response);
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().print("<script>alert('导出失败！');window.location='/scoredetail/manager';</script> ");
				
			} 
		}
		return null;

	}
	/**
	 * 提交审核
	 * @param id
	 * @return
	 */
	@RequestMapping("/change")
	@ResponseBody
	public Json check(HttpServletRequest request,HttpServletResponse response,String userid,String typeid) {
		Json j = new Json();
		try {
			//传入传出字符编码
		request.setCharacterEncoding(ENCODE);
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(ENCODE);
		String result=ReqJsonUtil.reqJson("02", "调用失败"); 	
		JSONObject requestData = new JSONObject();
		requestData.put("transtype", "0079");
		requestData.put("from_user_name", userid);
		requestData.put("type_id", typeid);
		requestData.put("transdate",  ReqJsonUtil.gettransdate());
		logger.info("预备卡券改为可使用接口请求报文:"+requestData.toString());
		String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
		logger.info("预备卡券改为可使用接口返回报文:"+repjsonstr);
		JSONObject repjson = JSONObject.fromObject(repjsonstr);
		String resultcode=repjson.getString("retcode");
		if(resultcode.equals("00")){
			logger.info("预备卡券改为可使用接口申请成功");
			j.setMsg("操作成功！");
			j.setSuccess(true);
		}else{
			j.setMsg("未知错误");
		}
		}  catch (Exception e) {
			logger.info("预备卡券改为可使用接口异常:"+ReqJsonUtil.errInfo(e));
			j.setMsg(e.getMessage());
		}
		

		return j;
	}

	    private static String convert(double value){
	        String sal = new DecimalFormat("#.00").format(value);
	        return sal;
		}
		public static void main(String[] args) {
			 DecimalFormat    df   = new DecimalFormat("#.##");   
			  Double dd = Double.parseDouble(df.format(1));
			  System.out.println(dd);
			  
		}

}
