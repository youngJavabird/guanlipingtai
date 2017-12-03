package light.mvc.controller.transdetail;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.transdetail.Transdetail;
import light.mvc.pageModel.transtype.Transtype;
import light.mvc.service.transdetail.TransdetailServiceI;
import light.mvc.utils.SystemParam;
import light.mvc.utils.UploadUtil;
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

@Controller
@RequestMapping("/transdetail")
public class TransdetailController extends BaseController {
	Logger logger = Logger.getLogger(TransdetailController.class.getName());
	
	List<Transdetail> list = null;
	@Autowired
	private TransdetailServiceI transdetailservice;
	@RequestMapping("/manager")
	public String manager() {
		return "/transdetail/transdetail";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Transdetail transdetail, PageFilter ph) {

		Grid grid = new Grid();
		Long count = transdetailservice.count(transdetail, ph);
		grid.setRows(transdetailservice.dataGrid(transdetail, ph));
		grid.setTotal(count);
		ph.setPage(1);
		ph.setRows(Integer.valueOf(count.toString()));
		list = transdetailservice.dataGrid(transdetail, ph);
		return grid;
	}
	
	/**
	 * 加载下拉框数据
	 * @return
	 */
	@RequestMapping("/combobox_data")
	@ResponseBody
	public JSONArray getTranstypeCbo(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Transtype> list = transdetailservice.getTranstypeCbo();
			Transtype transtype = new Transtype();
			transtype.setTranstype("全部");
			transtype.setTranstypename("全部");
			list.add(0, transtype);
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("getTranstypeCbo:" + e.getMessage());
		}
		return jsonarray;
	}
	
	@RequestMapping("/download")
	@ResponseBody
	public Json download(HttpServletRequest request,HttpServletResponse response){
		Json j = new Json();
		if (list == null || list.size() < 1) {
			j.setMsg("导出数据不能为空");
			return j;
		}

        try {
			HSSFWorkbook wb = new HSSFWorkbook();
	        //声明一个单子并命名
	        HSSFSheet sheet = wb.createSheet("交易明细");
	        //给单子名称一个长度
	        sheet.setDefaultColumnWidth((short)15);
	        // 生成一个样式  
	        HSSFCellStyle style = wb.createCellStyle();
	        //创建第一行（也可以称为表头）
	        HSSFRow row = sheet.createRow(1);
	        //创建第二行
	        HSSFRow row1 = sheet.createRow(0);
	        //样式字体居中
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        //给表头第一行一次创建单元格
	        HSSFCell cell1 = row1.createCell((short) 0);
	        cell1.setCellValue("交易明细");
	        cell1.setCellStyle(style);
	        
	        //给表头第二行一次创建单元格
	        HSSFCell cell = row.createCell((short) 0);
	        cell.setCellValue("序号"); 
	        cell.setCellStyle(style);
	        cell = row.createCell( (short) 1);  
	        cell.setCellValue("交易类型");  
	        cell.setCellStyle(style);  
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("费种");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("条形码");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("中心流水号");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("交易时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("金额(元)");  
	        cell.setCellStyle(style); 
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("使用积分");  
	        cell.setCellStyle(style); 
	        sheet.addMergedRegion(new Region(0,(short)0,0,(short)6));
	        //向单元格里填充数据
	           for (short i = 0; i < list.size(); i++) {
	            row = sheet.createRow(i + 2);
	            row.createCell(0).setCellValue(i+1);
	            row.createCell(1).setCellValue(list.get(i).getTranstype());
	            row.createCell(2).setCellValue(list.get(i).getCode());
	            row.createCell(3).setCellValue(list.get(i).getBarcode());
	            row.createCell(4).setCellValue(list.get(i).getTrsno_center());
	            row.createCell(5).setCellValue(list.get(i).getTrsdate_send());
	            row.createCell(6).setCellValue(list.get(i).getAmount());
	            row.createCell(7).setCellValue(list.get(i).getScore());
	        }
         
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(new Date());
            String path = SystemParam.uploadmanagepath+"/"+date+".xls";
            FileOutputStream out = new FileOutputStream(path);
            wb.write(out);
            out.close();
            
            boolean bool = UploadUtil.download(path, response);
            if (bool) {
            	j.setSuccess(true);
			}
            else {
            	j.setMsg("导出失败!!!");
			}
        } catch (Exception e) {
            e.printStackTrace();
			j.setMsg("系统异常,请联系管理员！");
        } 
		return j;
	}
	
}
