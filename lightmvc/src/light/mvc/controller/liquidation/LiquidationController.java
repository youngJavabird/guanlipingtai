package light.mvc.controller.liquidation;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.controller.goods.ProductController;
import light.mvc.controller.refund.RefundController;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;
import light.mvc.pageModel.wnfcard.Cardd;
import light.mvc.service.base.ServiceException;
import light.mvc.service.card.CardtypeServiceI;


import light.mvc.service.liquidation.LiquidationServiceI;
import light.mvc.service.refund.RefundServiceI;
import light.mvc.service.wnfcard.CarddServiceI;
import light.mvc.utils.FromExlsUtil;
import light.mvc.utils.ImportExcelUtil;
import light.mvc.utils.ReqJsonUtil;
import light.mvc.utils.ResponseCodeDefault;
import light.mvc.utils.RsaUtil;
import light.mvc.utils.SSocketClientImpl;
import light.mvc.utils.SocketUtil;
import light.mvc.utils.StringUtil;
import light.mvc.utils.UploadUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;


























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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;









@Controller
@RequestMapping("/liquidation")
public class LiquidationController extends BaseController {
	Logger logger = Logger.getLogger(LiquidationController.class.getName());
	private final String ENCODE = "UTF-8";
	private final String CONTENT_TYPE = "text/html;charset=UTF-8";
	Scoredetail o_scoredetail=null;
	@Autowired
	private LiquidationServiceI liquidationService;

	@RequestMapping("/manager")
	public String manager() {
		return "/liquidation/liquidation";
	}


	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Scoredetail scoredetail, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(liquidationService.dataGrid(scoredetail, ph));
		grid.setTotal(liquidationService.count(scoredetail, ph));
		o_scoredetail =scoredetail;
		return grid;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping("/exportaccountinfo")
	private String ExportOrderInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		PageFilter ph = new PageFilter();
		ph.setRows(99999999);
		List<Scoredetail> list = liquidationService.dataGrid(o_scoredetail, ph);
		
		if (o_scoredetail == null||o_scoredetail.equals(null)) {
			String sendmessage="无导出数据";
			request.setAttribute("sendmessage", sendmessage);
			return "/liquidation/liquidation";
		}
		else{
			HSSFWorkbook wb = new HSSFWorkbook();
			//声明一个单子并命名
			HSSFSheet sheet = wb.createSheet("乐购抵用清算汇总表");
			//给单子名称一个长度
			sheet.setColumnWidth(0,(int)((3 + 0.72) * 256));
			sheet.setColumnWidth(1,(int)((33 + 0.72) * 256));
			sheet.setColumnWidth(2,(int)((20 + 0.72) * 256));
			sheet.setColumnWidth(3,(int)((20 + 0.72) * 256));
			sheet.setColumnWidth(4,(int)((10 + 0.72) * 256));


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
			cell1.setCellValue("乐购抵用清算汇总");
			cell1.setCellStyle(style);
	
			//给表头第二行一次创建单元格
			HSSFCell cell2 = row1.createCell((short) 0);
			cell2.setCellValue("编号");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 1);  
			cell2.setCellValue("openid(微信用户ID)");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 2);  
			cell2.setCellValue("张数");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 3);  
			cell2.setCellValue("购买价格");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 4);  
			cell2.setCellValue("券面金额");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 5);  
			cell2.setCellValue("日期");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 6);  
			
			 sheet.addMergedRegion(new Region(0,(short)0,0,(short)15)); 
			 
//			 DecimalFormat    df   = new DecimalFormat("######0.00");   
//			  Double dd = Double.parseDouble(df.format(d));
			//向单元格里填充数据
			for (short i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 2);
				row.createCell(0).setCellValue(i+1);
				row.createCell(1).setCellValue(list.get(i).getUserid());
				row.createCell(2).setCellValue(list.get(i).getOrderid());
				row.createCell(3).setCellValue(list.get(i).getPhone());
				row.createCell(4).setCellValue(list.get(i).getBalance());
				row.createCell(5).setCellValue(list.get(i).getPbalance());			

			}
			
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = df.format(new Date());
				System.out.println(System.getProperty("upload") );
				String path = "../"+date+".xls";
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
}
