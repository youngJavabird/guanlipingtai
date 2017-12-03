package light.mvc.controller.statistics;


import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import light.mvc.model.starwin.TStarWin;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.pageModel.statistics.Identification;
import light.mvc.pageModel.statistics.Statistics;
import light.mvc.service.cardchannel.Impl.CardchannelServiceImpl;
import light.mvc.service.starwin.WnfxhaccountServiceI;
import light.mvc.service.statistics.StatisticsServiceI;
import light.mvc.utils.SystemParam;
import light.mvc.utils.UploadUtil;


@Controller
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {
	
	Logger logger = Logger.getLogger(StatisticsController.class.getName());

	private List<Identification> list;
	private List<Identification> activitylist;
	private List<Statistics> listone;
	private List<Statistics> listtwo;
	@Autowired
	private StatisticsServiceI statisticsServiceI;

	@RequestMapping("/manager")
	public String manager() {
		return "/statistics/statistics";
	}
	
	@RequestMapping("/managerr")
	public String managerr() {
		return "/statistics/users";
	}
	
	@RequestMapping("/activitymanager")
	public String activitymanager() {
		return "/statistics/activity";
	}
	
    //所有模板pv uv
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(HttpServletRequest request,HttpServletResponse response,Identification identification) {
		Grid grid = new Grid();
		try {
		grid.setRows(statisticsServiceI.dataGrid(identification));
		grid.setTotal(statisticsServiceI.count(identification));
	    list =statisticsServiceI.dataGrid(identification);
		} catch (Exception e) {
			System.out.println("1"+e);
			logger.info("StatisticsController:所有模板pv uv" + e.getMessage());
			
		} 
		return grid;
		
	}
	
	//公众号活动商城PV UV
	@RequestMapping("/activity")
	@ResponseBody
	public Grid activity(HttpServletRequest request,HttpServletResponse response,Identification identification) {
		Grid grid = new Grid();
		try {
		grid.setRows(statisticsServiceI.activity(identification));
		grid.setTotal(statisticsServiceI.count(identification));
	    activitylist =statisticsServiceI.activity(identification);
		} catch (Exception e) {
			System.out.println("3"+e);
			logger.info("StatisticsController:公众号活动商城PV UV" + e.getMessage());
			
		} 
		return grid;
		
	}
	//分渠道导出所有用户信息
	@RequestMapping("/dataGridd")
	@ResponseBody
	public Grid dataGridd(HttpServletRequest request,HttpServletResponse response,Statistics statistics) throws Exception {
		Grid grid = new Grid();
		
		grid.setRows(statisticsServiceI.dataGrid(statistics));
		grid.setTotal(statisticsServiceI.count(statistics));
		try {
		 listone =statisticsServiceI.listone(statistics);	
		 } catch (Exception e) {
				System.out.println("1"+e);
				logger.info("StatisticsController:分渠道导出所有用户信息" + e.getMessage());
				
			} 
		try {
		 listtwo =statisticsServiceI.listtwo(statistics);
	    } catch (Exception e) {
 		  System.out.println("2"+e);
		  logger.info("StatisticsController:分渠道导出所有用户信息" + e.getMessage());
		
	} 
	
		return grid;
	}
	

	@RequestMapping("/exportinfo")
	private String ExportOrderInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (list == null || list.size() < 1) {
			String sendmessage="无导出数据";
			request.setAttribute("sendmessage", sendmessage);
			return "/statistics/statistics";
		}
		else{
			HSSFWorkbook wb = new HSSFWorkbook();
			//声明一个单子并命名
			HSSFSheet sheet = wb.createSheet("数据访问流量统计");
			
			//给单子名称一个长度
			sheet.setDefaultColumnWidth((short)13);
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
			cell1.setCellValue("数据访问流量统计");
			cell1.setCellStyle(style);
			
	
			//给表头第二行一次创建单元格
			HSSFCell cell2 = row1.createCell((short) 0);
			cell2.setCellValue("时间");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 1);  
			cell2.setCellValue("名称");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 2);  
			cell2.setCellValue("PV");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 3);  
			cell2.setCellValue("UV");
			cell2.setCellStyle(style);
			sheet.addMergedRegion(new Region(0,(short)0,0,(short)3)); 
			//向单元格里填充数据
			for (short i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 3);
				row.createCell(0).setCellValue(list.get(i).getDete());
				row.createCell(1).setCellValue(list.get(i).getName()+list.get(i).getType());
				row.createCell(2).setCellValue(list.get(i).getPv());
				row.createCell(3).setCellValue(list.get(i).getUv());
			}
			
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = df.format(new Date());
				System.out.println(System.getProperty("upload") );
				String path = SystemParam.uploadpath+"/"+date+".xls";
				FileOutputStream out = new FileOutputStream(path);
				wb.write(out);
				out.close();
				UploadUtil.download(path, response);
			} catch (Exception e) {
				logger.info("StatisticsController:" + e.getMessage());
				response.getWriter().print("<script>alert('导出失败！');window.location='/starwin/manager';</script> ");
				
			} 
		}
		return null;

	}
	@RequestMapping("/exportactivityinfo")
	private String exportactivityinfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (activitylist == null || activitylist.size() < 1) {
			String sendmessage="无导出数据";
			request.setAttribute("sendmessage", sendmessage);
			return "/statistics/activity";
		}
		else{
			HSSFWorkbook wb = new HSSFWorkbook();
			//声明一个单子并命名
			HSSFSheet sheet = wb.createSheet("商城活动流量统计");
			
			//给单子名称一个长度
			sheet.setDefaultColumnWidth((short)13);
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
			cell1.setCellValue("数据访问流量统计");
			cell1.setCellStyle(style);
			
	
			//给表头第二行一次创建单元格
			HSSFCell cell2 = row1.createCell((short) 0);
			cell2.setCellValue("时间");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 1);  
			cell2.setCellValue("模板名称");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 2);  
			cell2.setCellValue("类型");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 3);  
			cell2.setCellValue("PV");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 4);  
			cell2.setCellValue("UV");
			cell2.setCellStyle(style);
			sheet.addMergedRegion(new Region(0,(short)0,0,(short)4)); 
			//向单元格里填充数据
			for (short i = 0; i < activitylist.size(); i++) {
				row = sheet.createRow(i + 3);
				row.createCell(0).setCellValue(activitylist.get(i).getDete());
				row.createCell(1).setCellValue(activitylist.get(i).getUserid());
				row.createCell(2).setCellValue(activitylist.get(i).getName());
				row.createCell(3).setCellValue(activitylist.get(i).getPv());
				row.createCell(4).setCellValue(activitylist.get(i).getUv());
			}
			
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = df.format(new Date());
				System.out.println(System.getProperty("upload") );
				String path = SystemParam.uploadpath+"/"+date+".xls";
				FileOutputStream out = new FileOutputStream(path);
				wb.write(out);
				out.close();
				UploadUtil.download(path, response);
			} catch (Exception e) {
				logger.info("StatisticsController:" + e.getMessage());
				response.getWriter().print("<script>alert('导出失败！');window.location='/starwin/manager';</script> ");
				
			} 
		}
		return null;

	}
	@RequestMapping("/exportuserinfo")
	private String exportuserinfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    if(listone==null&&listtwo==null){
		String sendmessage="无导出数据";
		request.setAttribute("sendmessage", sendmessage);
		return "/statistics/users";
	}
		if (listone.size() < 1&& listtwo.size() < 1) {
			String sendmessage="无导出数据";
			request.setAttribute("sendmessage", sendmessage);
			return "/statistics/users";
		}
		else{
			HSSFWorkbook wb = new HSSFWorkbook();
			//声明一个单子并命名sheet1
			HSSFSheet sheet = wb.createSheet("渠道001");

			//给单子名称一个长度
			sheet.setDefaultColumnWidth((short)13);
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
			cell1.setCellValue("用户流量详情");
			cell1.setCellStyle(style);
			
			//声明一个单子并命名sheet2

			HSSFSheet sheet2 = wb.createSheet("渠道002");
			//给单子名称一个长度
			sheet2.setDefaultColumnWidth((short)13);
			//创建第一行（也可以称为表头）
			HSSFRow rowtwo = sheet2.createRow(0);
			//创建第二行
			HSSFRow rowtwo1 = sheet2.createRow(2);
			//样式字体居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			//给表头第一行一次创建单元格
			HSSFCell celltwo1 = rowtwo.createCell((short) 0);
			celltwo1.setCellValue("用户流量详情");
			celltwo1.setCellStyle(style);
			
	
			//给表头第二行一次创建单元格
			HSSFCell cell2 = row1.createCell((short) 0);
			cell2.setCellValue("时间");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 1);  
			cell2.setCellValue("IP");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 2);  
			cell2.setCellValue("姓名");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 3);  
			cell2.setCellValue("拼音名");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 4);  
			cell2.setCellValue("邮箱");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 5);  
			cell2.setCellValue("学校");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 6);  
			cell2.setCellValue("专业");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 7);  
			cell2.setCellValue("电话");
			cell2.setCellStyle(style);
			sheet.addMergedRegion(new Region(0,(short)0,0,(short)7)); 
			
			//给表头第二行一次创建单元格
			HSSFCell celltwo2 = rowtwo1.createCell((short) 0);
			celltwo2.setCellValue("时间");
			celltwo2.setCellStyle(style);
			celltwo2 = rowtwo1.createCell( (short) 1);  
			celltwo2.setCellValue("IP");
			celltwo2.setCellStyle(style);
			celltwo2 = rowtwo1.createCell( (short) 2);  
			celltwo2.setCellValue("姓名");
			celltwo2.setCellStyle(style);
			celltwo2 = rowtwo1.createCell( (short) 3);  
			celltwo2.setCellValue("拼音名");
			celltwo2.setCellStyle(style);
			celltwo2 = rowtwo1.createCell( (short) 4);  
			celltwo2.setCellValue("邮箱");
			celltwo2.setCellStyle(style);
			celltwo2 = rowtwo1.createCell( (short) 5);  
			celltwo2.setCellValue("学校");
			celltwo2.setCellStyle(style);
			celltwo2 = rowtwo1.createCell( (short) 6);  
			celltwo2.setCellValue("专业");
			celltwo2.setCellStyle(style);
			celltwo2 = rowtwo1.createCell( (short) 7);  
			celltwo2.setCellValue("电话");
			celltwo2.setCellStyle(style);
			sheet2.addMergedRegion(new Region(0,(short)0,0,(short)7)); 
			//向sheet1单元格里填充数据
			for (short i = 0; i < listone.size(); i++) {
				row = sheet.createRow(i + 3);
				row.createCell(0).setCellValue(listone.get(i).getAccesstime());
				row.createCell(1).setCellValue(listone.get(i).getIp());
				row.createCell(2).setCellValue(listone.get(i).getName());
				row.createCell(3).setCellValue(listone.get(i).getEname());
				row.createCell(4).setCellValue(listone.get(i).getEmail());
				row.createCell(5).setCellValue(listone.get(i).getSchool());
				row.createCell(6).setCellValue(listone.get(i).getMajor());
				row.createCell(7).setCellValue(listone.get(i).getPhone());
			}
			//向sheet2单元格里填充数据
			for (short i = 0; i < listtwo.size(); i++) {
				rowtwo = sheet2.createRow(i + 3);
				rowtwo.createCell(0).setCellValue(listtwo.get(i).getAccesstime());
				rowtwo.createCell(1).setCellValue(listtwo.get(i).getIp());
				rowtwo.createCell(2).setCellValue(listtwo.get(i).getName());
				rowtwo.createCell(3).setCellValue(listtwo.get(i).getEname());
				rowtwo.createCell(4).setCellValue(listtwo.get(i).getEmail());
				rowtwo.createCell(5).setCellValue(listtwo.get(i).getSchool());
				rowtwo.createCell(6).setCellValue(listtwo.get(i).getMajor());
				rowtwo.createCell(7).setCellValue(listtwo.get(i).getPhone());
			}
			
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = df.format(new Date());
				System.out.println(System.getProperty("upload") );
				String path = SystemParam.uploadpath+"/"+date+".xls";
				FileOutputStream out = new FileOutputStream(path);
				wb.write(out);
				out.close();
				UploadUtil.download(path, response);
			} catch (Exception e) {
				logger.info("StatisticsController:" + e.getMessage());
				response.getWriter().print("<script>alert('导出失败！');window.location='/starwin/manager';</script> ");
				
			} 
		}
		return null;

	}

	@RequestMapping("/name_combox")
	@ResponseBody
	public JSONArray card_type_combox() {
		List<Identification> cl = statisticsServiceI.name_combox();
		JSONArray jsonArray = JSONArray.fromObject(cl);
		return jsonArray;
	}

}
