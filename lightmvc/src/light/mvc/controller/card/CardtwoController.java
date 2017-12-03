package light.mvc.controller.card;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;
import light.mvc.service.base.ServiceException;
import light.mvc.service.card.CardtypeServiceI;


import light.mvc.service.card.CardtypetwoServiceI;
import light.mvc.utils.SystemParam;
import light.mvc.utils.UploadUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;

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


@Controller
@RequestMapping("/cardtwo")
public class CardtwoController extends BaseController {

	@Autowired
	private CardtypetwoServiceI cardservicetwo;
	
	Card o_card=null;

	@RequestMapping("/manager")
	public String manager() {
		return "/card/cardtwo";
	}


	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Card card, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(cardservicetwo.dataGrid(card, ph));
		grid.setTotal(cardservicetwo.count(card, ph));
		o_card =card;
		return grid;
	}
	
	@RequestMapping("/exportcardinfo")
	private String ExportOrderInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		PageFilter ph = new PageFilter();
		ph.setRows(99999999);
		List<Card> list = cardservicetwo.dataGrid(o_card, ph);
		
		if (o_card == null||o_card.equals(null)) {
			String sendmessage="无导出数据";
			request.setAttribute("sendmessage", sendmessage);
			return "/card/cardtwo";
		}
		else{
			HSSFWorkbook wb = new HSSFWorkbook();
			//声明一个单子并命名
			HSSFSheet sheet = wb.createSheet("卡券详情信息表");
			//给单子名称一个长度
//			sheet.setColumnWidth(0,(int)((3 + 0.72) * 256));
//			sheet.setColumnWidth(1,(int)((33 + 0.72) * 256));
//			sheet.setColumnWidth(2,(int)((20 + 0.72) * 256));
			sheet.setColumnWidth(1,(int)((45 + 0.72) * 256));
//			sheet.setColumnWidth(4,(int)((10 + 0.72) * 256));
//			sheet.setColumnWidth(8,(int)((20 + 0.72) * 256));
//			sheet.setColumnWidth(13,(int)((20 + 0.72) * 256));
//			sheet.setColumnWidth(15,(int)((20 + 0.72) * 256));

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
			cell1.setCellValue("卡券信息详情");
			cell1.setCellStyle(style);
	
			//给表头第二行一次创建单元格
			HSSFCell cell2 = row1.createCell((short) 0);
			cell2.setCellValue("TYPEID");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 1);  
			cell2.setCellValue("券类型");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 2);  
			cell2.setCellValue("库存张/套数");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 3);  
			cell2.setCellValue("已购买套券");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 4);  
			cell2.setCellValue("未激活卡券");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 5);  
			cell2.setCellValue("预备卡券");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 6);  
			cell2.setCellValue("已激活");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 7);  
			cell2.setCellValue("已退券未审核");
			cell2.setCellStyle(style);			
			cell2 = row1.createCell( (short) 8);  
			cell2.setCellValue("已退券已审核");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 9);  
			cell2.setCellValue("发券失败");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 10);  
			cell2.setCellValue("已使用");
			cell2.setCellStyle(style);
			cell2 = row1.createCell( (short) 11);  
			
			 sheet.addMergedRegion(new Region(0,(short)0,0,(short)11)); 
			 
//			 DecimalFormat    df   = new DecimalFormat("######0.00");   
//			  Double dd = Double.parseDouble(df.format(d));
			//向单元格里填充数据
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i + 2);
				row.createCell(0).setCellValue(list.get(i).getCard_typeid());
				row.createCell(1).setCellValue(list.get(i).getCard_name());
				row.createCell(2).setCellValue(list.get(i).getGuid());
				row.createCell(3).setCellValue(list.get(i).getCard_detile());
				row.createCell(4).setCellValue(list.get(i).getCard_picture());			
				row.createCell(5).setCellValue(list.get(i).getCard_colour());
				row.createCell(6).setCellValue(list.get(i).getCard_code());
				row.createCell(7).setCellValue(list.get(i).getCreatedate());
				row.createCell(8).setCellValue(list.get(i).getCard_userid());
				row.createCell(9).setCellValue(list.get(i).getCard_bar_code());
				row.createCell(10).setCellValue(list.get(i).getAaaa());
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


}
