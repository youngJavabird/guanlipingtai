package light.mvc.controller.starwin;


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
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.service.starwin.WnfxhaccountServiceI;
import light.mvc.utils.UploadUtil;


@Controller
@RequestMapping("/starwin")
public class WnfXhaccountController extends BaseController {

	private List<Starwin> list;
	@Autowired
	private WnfxhaccountServiceI wnfxhaccountService;

	@RequestMapping("/manager")
	public String manager() {
		return "/starwin/wnfaccount";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Starwin starwin, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(wnfxhaccountService.dataGrid(starwin, ph));
		grid.setTotal(wnfxhaccountService.count(starwin, ph));
		list =wnfxhaccountService.dataGrid(starwin);
		return grid;
	}
	
	/*@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,Starwin starwin) {
		Grid grid = new Grid();	
		
		grid.setRows(wnfxhaccountService.dataGrid(starwin));
		grid.setTotal(wnfxhaccountService.count(starwin));
		return grid;
	}*/

	@RequestMapping("/addPage")
	public String addPage() {
		return "/starwin/wnfaccountadd";
	}
	@RequestMapping("/exportaccountinfo")
	private String ExportOrderInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (list == null || list.size() < 1) {
			String sendmessage="无导出数据";
			request.setAttribute("sendmessage", sendmessage);
			return "/starwin/wnfaccount";
		}
		else{
			HSSFWorkbook wb = new HSSFWorkbook();
			//声明一个单子并命名
			HSSFSheet sheet = wb.createSheet("星河账户信息表");
			//给单子名称一个长度
			sheet.setDefaultColumnWidth((short)20);
			// 生成一个样式  
			HSSFCellStyle style = wb.createCellStyle();
			//创建第一行（也可以称为表头）
			HSSFRow row = sheet.createRow(0);
			//创建第二行
			HSSFRow row1 = sheet.createRow(2);
	
			//样式字体居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			//给表头第一行一次创建单元格
			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setCellValue("星河账户信息详情");
			cell1.setCellStyle(style);
	
			//给表头第二行一次创建单元格
			HSSFCell cell2 = row1.createCell((short) 0);
			cell2.setCellValue("序号");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 1);  
			cell2.setCellValue("姓名");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 2);  
			cell2.setCellValue("密码");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 3);  
			cell2.setCellValue("注册时间");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 4);  
			cell2.setCellValue("手机号");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 5);  
			cell2.setCellValue("是否点击下载星合");
			cell2.setCellStyle(style);
			 sheet.addMergedRegion(new Region(0,(short)0,0,(short)5)); 
	
			//向单元格里填充数据
			for (short i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 3);
				row.createCell(0).setCellValue(i+1);
				row.createCell(1).setCellValue(list.get(i).getName());
				row.createCell(2).setCellValue(list.get(i).getPasswd());
				row.createCell(3).setCellValue(list.get(i).getCreatedate().toString());
				row.createCell(4).setCellValue(list.get(i).getPhone());
				if(list.get(i).getClick_download().equals("00")){
				row.createCell(5).setCellValue("未点击");
				}else{
				row.createCell(5).setCellValue("已点击");	
				}
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
				response.getWriter().print("<script>alert('导出失败！');window.location='/starwin/manager';</script> ");
				
			} 
		}
		return null;

	}



}
