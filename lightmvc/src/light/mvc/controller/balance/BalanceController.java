package light.mvc.controller.balance;


import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import light.mvc.model.starwin.TStarWin;
import light.mvc.pageModel.balance.Balance;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.orderinfo.OrderinfoView;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.service.balance.BalanceServiceI;
import light.mvc.service.scoredetail.ScoredetailServiceI;
import light.mvc.service.starwin.WnfxhaccountServiceI;
import light.mvc.utils.UploadUtil;


@Controller
@RequestMapping("/balance")
public class BalanceController extends BaseController {

	Balance o_balance=null;
	@Autowired
	private BalanceServiceI balanceServiceI;

	@RequestMapping("/manager")
	public String manager() {
		return "/balance/balance";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Balance balance, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(balanceServiceI.dataGrid(balance, ph));
		grid.setTotal(balanceServiceI.count(balance, ph));
		o_balance =balance;
		return grid;
	}
	
	@RequestMapping("/exportbalanceinfo")
	private String ExportOrderInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		PageFilter ph = new PageFilter();
		ph.setRows(99999999);
		List<Balance> list = balanceServiceI.dataGrid(o_balance, ph);
		
		if (o_balance == null||o_balance.equals(null)) {
			String sendmessage="无导出数据";
			request.setAttribute("sendmessage", sendmessage);
			return "/balance/balance";
		}
		else{
			HSSFWorkbook wb = new HSSFWorkbook();
			//声明一个单子并命名
			HSSFSheet sheet = wb.createSheet("用户账户余额");
			//给单子名称一个长度
			sheet.setDefaultColumnWidth((short)20);
			// 生成一个样式  
			HSSFCellStyle style = wb.createCellStyle();
			//创建第一行（也可以称为表头）
			HSSFRow row = sheet.createRow(0);
			//创建第二行
			HSSFRow row1 = sheet.createRow(1);
	
			//样式字体居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			//给表头第一行一次创建单元格
			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue("用户账户余额");
			cell1.setCellStyle(style);
	
			//给表头第二行一次创建单元格
			HSSFCell cell2 = row1.createCell((short) 0);
			cell2.setCellValue("编号");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 1);  
			cell2.setCellValue("openid(微信用户ID)");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 2);  
			cell2.setCellValue("手机号");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 3);  
			cell2.setCellValue("帐户余额（包含返现）");
			cell2.setCellStyle(style);
			
			 sheet.addMergedRegion(new Region(0,(short)0,0,(short)3)); 
	
			//向单元格里填充数据
			for (short i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 2);
				row.createCell(0).setCellValue(i+1);
				row.createCell(1).setCellValue(list.get(i).getUserid());
				row.createCell(2).setCellValue(list.get(i).getPhone());
				row.createCell(3).setCellValue(list.get(i).getBalance());
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
				response.getWriter().print("<script>alert('导出失败！');window.location='/balance/manager';</script> ");
				
			} 
		}
		return null;

	}



}
