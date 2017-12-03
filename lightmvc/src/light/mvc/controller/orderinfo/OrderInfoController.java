package light.mvc.controller.orderinfo;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.orderinfo.CardUtilBean;
import light.mvc.pageModel.orderinfo.Orderinfo;
import light.mvc.pageModel.orderinfo.OrderinfoView;
import light.mvc.pageModel.usercard.Usercard;
import light.mvc.service.goods.ProductServiceI;
import light.mvc.service.orderinfo.OrderInfoServiceI;
import light.mvc.utils.CardUtil;
import light.mvc.utils.ReFund;
import light.mvc.utils.StringUtil;
import light.mvc.utils.SystemParam;
import light.mvc.utils.UploadUtil;
import light.mvc.utils.WxToUserMessageUtil;
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

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/orderinfo")
public class OrderInfoController extends BaseController {
	Logger logger = Logger.getLogger(OrderInfoController.class.getName());
	
	//保存条件信息
	Orderinfo g_orderinfo = null;

	@Autowired
	private OrderInfoServiceI orderInfoService;
	
	@Autowired
	private ProductServiceI productService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/orderinfo/orderinfo";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Orderinfo orderinfo, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(orderInfoService.dataGrid(orderinfo, ph));
		grid.setTotal(orderInfoService.count(orderinfo, ph));
		g_orderinfo = orderinfo;
		return grid;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Orderinfo orderinfo = orderInfoService.get(id);
		request.setAttribute("orderinfo", orderinfo);
		return "/orderinfo/editSupplier";
	}
	
	/**
	 * 修改订单信息
	 * @param orderinfo
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Orderinfo orderinfo){
		Json j = new Json();
		try {
			Orderinfo o = orderInfoService.get(orderinfo.getId());
			o.setUpddate((new Date()));
			o.setLogistics(orderinfo.getLogistics());
			o.setLogisticsnum(orderinfo.getLogisticsnum());
			orderInfoService.edit(o);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			logger.info("SmstempEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/importPage")
	public String importPage(HttpServletRequest request) {
		return "/orderinfo/importSupplier";
	}
	
	
	
	/**
	 * 导出Excel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	@ResponseBody
	public Json downLoad(HttpServletRequest request,HttpServletResponse response){
		Json j = new Json();
		if (g_orderinfo == null) {
			j.setMsg("导出数据不能为空");
			return j;
		}
		
		PageFilter ph = new PageFilter();
		ph.setRows(99999999);
		List<OrderinfoView> list = orderInfoService.dataGrid(g_orderinfo, ph);
		if (list.size() < 1) {
			j.setMsg("导出数据不能为空");
			return j;
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
        //声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("导出信息明细");
        //给单子名称一个长度
        sheet.setDefaultColumnWidth((short)15);
        // 生成一个样式  
        HSSFCellStyle style = wb.createCellStyle();
        //创建第一行（也可以称为表头）
        HSSFRow row = sheet.createRow(2);
        //创建第二行
        HSSFRow row1 = sheet.createRow(0);
        //创建第三行
        HSSFRow row2 = sheet.createRow(1);
        //样式字体居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //给表头第一行一次创建单元格
        HSSFCell cell1 = row1.createCell((short) 0);
        cell1.setCellValue("导出信息明细");
        cell1.setCellStyle(style);
        
        //给表头第二行一次创建单元格
        HSSFCell cell2 = row2.createCell((short) 0);
        cell2.setCellValue("编号");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 1);  
        cell2.setCellValue("订单信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 5);  
        cell2.setCellValue("订单产品信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 10);  
        cell2.setCellValue("供货商信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 18);  
        cell2.setCellValue("订单金额");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 20);  
        cell2.setCellValue("券码信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 27);  
        cell2.setCellValue("收货人信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 33);  
        cell2.setCellValue("发票信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 35);  
        cell2.setCellValue("用户平台");
        cell2.setCellStyle(style);
        
        //给表头第三行一次创建单元格
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("序号"); 
        cell.setCellStyle(style);
        cell = row.createCell( (short) 1);  
        cell.setCellValue("订单号");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("下单时间");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 3);  
        cell.setCellValue("付款时间");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 4);  
        cell.setCellValue("订单状态");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 5);  
        cell.setCellValue("产品名称");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 6);  
        cell.setCellValue("品牌");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 7);  
        cell.setCellValue("属性");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 8);  
        cell.setCellValue("规格");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 9);  
        cell.setCellValue("数量");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 10);  
        cell.setCellValue("供应商名称");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 11);  
        cell.setCellValue("供货价");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 12);  
        cell.setCellValue("销售价");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 13);  
        cell.setCellValue("券码价格");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 14);  
        cell.setCellValue("运费");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 15);  
        cell.setCellValue("成本");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 16);  
        cell.setCellValue("税费");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 17);  
        cell.setCellValue("利率");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 18);  
        cell.setCellValue("平台单价");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 19);  
        cell.setCellValue("平台订单总额");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 20);  
        cell.setCellValue("券码品牌");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 21);  
        cell.setCellValue("券码名称");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 22);  
        cell.setCellValue("券码价格");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 23);  
        cell.setCellValue("券号");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 24);  
        cell.setCellValue("券码密码");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 25);  
        cell.setCellValue("券码有效期");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 26);  
        cell.setCellValue("券码状态");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 27);  
        cell.setCellValue("姓名");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 28);  
        cell.setCellValue("手机号");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 29);  
        cell.setCellValue("邮编");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 30);  
        cell.setCellValue("收件省市");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 31);  
        cell.setCellValue("地区");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 32);  
        cell.setCellValue("具体地址");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 33);  
        cell.setCellValue("发票类型");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 34);  
        cell.setCellValue("发票抬头");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 35);  
        cell.setCellValue("用户平台");  
        cell.setCellStyle(style); 
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)35)); 
        sheet.addMergedRegion(new Region(1,(short)1,1,(short)4)); 
        sheet.addMergedRegion(new Region(1,(short)5,1,(short)9)); 
        sheet.addMergedRegion(new Region(1,(short)10,1,(short)17)); 
        sheet.addMergedRegion(new Region(1,(short)18,1,(short)19)); 
        sheet.addMergedRegion(new Region(1,(short)20,1,(short)26)); 
        sheet.addMergedRegion(new Region(1,(short)27,1,(short)32)); 
        sheet.addMergedRegion(new Region(1,(short)33,1,(short)34)); 
        //向单元格里填充数据
           for (short i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 3);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(list.get(i).getOrderid());
            row.createCell(2).setCellValue(list.get(i).getCreatedate());
            row.createCell(3).setCellValue(list.get(i).getPaytime());
            row.createCell(4).setCellValue(list.get(i).getOrderstate());
            row.createCell(5).setCellValue(list.get(i).getDetail());
            row.createCell(6).setCellValue(list.get(i).getTypename());
            row.createCell(7).setCellValue(list.get(i).getFiled());
            row.createCell(8).setCellValue(list.get(i).getFildename());
            row.createCell(9).setCellValue(list.get(i).getNum());
            row.createCell(10).setCellValue(list.get(i).getSuppliername());
            row.createCell(11).setCellValue(list.get(i).getSupplierprice());
            
            row.createCell(12).setCellValue(list.get(i).getNew_price());
            row.createCell(13).setCellValue(list.get(i).getCard_price());
            //row.createCell(13).setCellFormula("J"+(i+4)+"*M"+(i+4));
            row.createCell(14).setCellValue(0);
            row.createCell(15).setCellFormula("L"+(i+4)+"+N"+(i+4)+"+O"+(i+4));
            row.createCell(16).setCellFormula("(M"+(i+4)+"-L"+(i+4)+"-O"+(i+4)+")*0.17");
            row.createCell(17).setCellFormula("M"+(i+4)+"-P"+(i+4)+"-Q"+(i+4));
            
            row.createCell(18).setCellValue(list.get(i).getNew_price());
            row.createCell(19).setCellValue(list.get(i).getCountprice());
            row.createCell(20).setCellValue(list.get(i).getRemark());
            row.createCell(21).setCellValue(list.get(i).getCard_name());
            row.createCell(22).setCellValue(list.get(i).getCard_price());
            row.createCell(23).setCellValue(list.get(i).getCard_code());
            row.createCell(24).setCellValue(list.get(i).getCard_password());
            row.createCell(25).setCellValue(list.get(i).getCard_endtime());
            row.createCell(26).setCellValue(list.get(i).getCardstate());
            row.createCell(27).setCellValue(list.get(i).getUsername());
            row.createCell(28).setCellValue(list.get(i).getPhone());
            row.createCell(29).setCellValue(list.get(i).getZipcode());
            row.createCell(30).setCellValue(list.get(i).getProvinces());
            row.createCell(31).setCellValue(list.get(i).getArea());
            row.createCell(32).setCellValue(list.get(i).getAddress());
            String invoicename = "";
            if (list.get(i).getIsinvoice() != null && list.get(i).getIsinvoice().equals("00")) {
            	invoicename = "无";
			}
            else if (list.get(i).getIsinvoice() != null && list.get(i).getIsinvoice().equals("01")) {
            	invoicename = "个人";
			}
            else if (list.get(i).getIsinvoice() != null && list.get(i).getIsinvoice().equals("02")) {
            	invoicename = "公司";
			}
            row.createCell(33).setCellValue(invoicename);
            row.createCell(34).setCellValue(list.get(i).getInvoicename());
            String userTypeName = "";
            if (list.get(i).getUsertype() != null && list.get(i).getUsertype().equals("1")) {
            	userTypeName = "APP用户";
			}
            else {
            	userTypeName = "微信用户";
			}
            row.createCell(35).setCellValue(userTypeName);
        }
        sheet.setForceFormulaRecalculation(true); 
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(new Date());
            System.out.println(System.getProperty("upload") );
            String path = SystemParam.uploadpath + "/orderInfo" + date + ".xls";
            System.out.println(path);
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
            return j;
        } catch (Exception e) {
        	logger.info("DownloadOrderInfo:"+e);
            e.printStackTrace();
            j.setMsg("系统异常,请联系管理员！");
			return j;
        } 
	}
	
	/**
	 * 导出Excel(供应商)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadsupplier")
	@ResponseBody
	public Json downLoadSupplier(HttpServletRequest request,HttpServletResponse response){
		Json j = new Json();
//		if (g_orderinfo == null || g_orderinfo.getState() == null || !g_orderinfo.getState().equals("01") || !g_orderinfo.getState().equals("02")) {
//			j.setMsg("只能导出状态为待发货和待收货的数据");
//			return j;
//		}
//		
		PageFilter ph = new PageFilter();
		ph.setRows(99999999);
		List<OrderinfoView> list = orderInfoService.dataGrid(g_orderinfo, ph);
		if (list.size() < 1) {
			j.setMsg("导出数据不能为空");
			return j;
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
        //声明一个单子并命名
        HSSFSheet sheet = wb.createSheet("订单信息(供货商)");
        //给单子名称一个长度
        sheet.setDefaultColumnWidth((short)15);
        // 生成一个样式  
        HSSFCellStyle style = wb.createCellStyle();
        //创建第一行（也可以称为表头）
        HSSFRow row = sheet.createRow(2);
        //创建第二行
        HSSFRow row1 = sheet.createRow(0);
        //创建第三行
        HSSFRow row2 = sheet.createRow(1);
        //样式字体居中
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //给表头第一行一次创建单元格
        HSSFCell cell1 = row1.createCell((short) 0);
        cell1.setCellValue("导出信息明细");
        cell1.setCellStyle(style);
        
        //给表头第二行一次创建单元格
        HSSFCell cell2 = row2.createCell((short) 0);
        cell2.setCellValue("编号");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 1);  
        cell2.setCellValue("订单信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 5);  
        cell2.setCellValue("订单产品信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 10);  
        cell2.setCellValue("供货商信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 12);  
        cell2.setCellValue("收货人信息");
        cell2.setCellStyle(style);
        cell2 = row2.createCell( (short) 18);  
        cell2.setCellValue("发票信息");
        cell2.setCellStyle(style);
        
        //给表头第三行一次创建单元格
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("序号"); 
        cell.setCellStyle(style);
        cell = row.createCell( (short) 1);  
        cell.setCellValue("订单号");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("下单时间");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 3);  
        cell.setCellValue("付款时间");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 4);  
        cell.setCellValue("订单状态");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 5);  
        cell.setCellValue("产品名称");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 6);  
        cell.setCellValue("品牌");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 7);  
        cell.setCellValue("规格");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 8);  
        cell.setCellValue("属性");  
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);  
        cell.setCellValue("数量");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 10);  
        cell.setCellValue("供应商名称");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 11);  
        cell.setCellValue("供货价");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 12);  
        cell.setCellValue("姓名");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 13);  
        cell.setCellValue("手机号");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 14);  
        cell.setCellValue("邮编");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 15);  
        cell.setCellValue("收件省市");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 16);  
        cell.setCellValue("地区");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 17);  
        cell.setCellValue("具体地址");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 18);  
        cell.setCellValue("发票类型");  
        cell.setCellStyle(style); 
        cell = row.createCell((short) 19);  
        cell.setCellValue("发票抬头");  
        cell.setCellStyle(style); 
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)19)); 
        sheet.addMergedRegion(new Region(1,(short)1,1,(short)4)); 
        sheet.addMergedRegion(new Region(1,(short)5,1,(short)9)); 
        sheet.addMergedRegion(new Region(1,(short)10,1,(short)11)); 
        sheet.addMergedRegion(new Region(1,(short)12,1,(short)17)); 
        sheet.addMergedRegion(new Region(1,(short)18,1,(short)19)); 
        //向单元格里填充数据
           for (short i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 3);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(list.get(i).getOrderid());
            row.createCell(2).setCellValue(list.get(i).getCreatedate());
            row.createCell(3).setCellValue(list.get(i).getPaytime());
            row.createCell(4).setCellValue(list.get(i).getOrderstate());
            row.createCell(5).setCellValue(list.get(i).getDetail());
            row.createCell(6).setCellValue(list.get(i).getTypename());
            row.createCell(7).setCellValue(list.get(i).getFildename());
            row.createCell(8).setCellValue(list.get(i).getFiled());
            row.createCell(9).setCellValue(list.get(i).getNum());
            row.createCell(10).setCellValue(list.get(i).getSuppliername());
            row.createCell(11).setCellValue(list.get(i).getSupplierprice());
            row.createCell(12).setCellValue(list.get(i).getUsername());
            row.createCell(13).setCellValue(list.get(i).getPhone());
            row.createCell(14).setCellValue(list.get(i).getZipcode());
            row.createCell(15).setCellValue(list.get(i).getProvinces());
            row.createCell(16).setCellValue(list.get(i).getArea());
            row.createCell(17).setCellValue(list.get(i).getAddress());String invoicename = "";
            if (list.get(i).getIsinvoice() != null && list.get(i).getIsinvoice().equals("00")) {
            	invoicename = "无";
			}
            else if (list.get(i).getIsinvoice() != null && list.get(i).getIsinvoice().equals("01")) {
            	invoicename = "个人";
			}
            else if (list.get(i).getIsinvoice() != null && list.get(i).getIsinvoice().equals("02")) {
            	invoicename = "公司";
			}
            row.createCell(18).setCellValue(invoicename);
            row.createCell(19).setCellValue(list.get(i).getInvoicename());
        }
         
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(new Date());
            String path = "../"+date+".xls";
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
            return j;
        } catch (Exception e) {
        	logger.info("DownloadOrderInfo:"+e);
            e.printStackTrace();
            j.setMsg("系统异常,请联系管理员！");
			return j;
        }
	}
	
	@RequestMapping("/updateorderstate")
	@ResponseBody
	public Json updateOrderState(Long id, String state, String remark){
		Json j = new Json();
		try {
			//获取订单信息
			Orderinfo orderinfo = orderInfoService.get(id);
			//获取状态
			System.out.println("状态进来为:"+state);
			System.out.println("订单号:"+orderinfo.getOrderid());
			List<CardUtilBean> list = orderInfoService.getCardType(orderinfo.getOrderid());
			if (StringUtil.isEmpty(list.get(0).getCardnum())) {
				list.get(0).setCardnum("0");
			}
			List<Card> wnflist = orderInfoService.queryCard(list.get(0).getCardnum(),list.get(0).getCardtypeid());
			//获取修改订单的状态
			String status = "";
			if (state.equals("0")) {//退货
				status = "05";
			} else if (state.equals("1")) {//撤销退货
				status = "02";
			} else if (state.equals("2")) {//换货
				status = "02";
			} else if (state.equals("3")) {//撤销换货
				status = "02";
			}
			
			//退货,退款
			if (state.equals("0")) {
				//商户号
				String mch_id = "";
				String appid = "";
				String appsecret = "";
				String cert = "";
				if (list.get(0).getUsertype() == 2) {//微信用户
					mch_id = SystemParam.mch_id;
					appid = SystemParam.appid; //微信公众号apid
					appsecret = SystemParam.appsecret;
					cert = SystemParam.cert;
				}
				else {//APP用户
					mch_id = SystemParam.APPmch_id;
					appid = SystemParam.APPappid;
					appsecret = SystemParam.APPappsecret;
					cert = SystemParam.certapp;
				}
				String total_fee = orderinfo.getTotalamount();
				String refund_fee = orderinfo.getRefundamount();
				
				String result = ReFund.wechatRefund(orderinfo.getOrderid(),orderinfo.getOrderid(),total_fee,refund_fee,mch_id,appid,appsecret,cert);
				
				logger.info("result:"+result);
				if (!result.equals("SUCCESS")) {
					logger.info(result);
					j.setMsg("退款失败,请重新退款或联系管理员");
		            return j;
				}
			}
			//撤销退货,生成卡券，生成成功后修改状态
			if (state.equals("1")) {
				if (wnflist.size()>0) {
					String json = "";
					//判断需要激活的卡券是否连续，如果连续则批量激活，否则单条激活
					if (Integer.parseInt(wnflist.get(wnflist.size()-1).getCard_seqno())-Integer.parseInt(wnflist.get(0).getCard_seqno())==(wnflist.size()-1)) {
						CardUtil.send(wnflist, list.get(0).getCardtypeid());
					}
					else {
						for (int i = 0; i < wnflist.size(); i++) {
							List<Card> li = new ArrayList<Card>();
							li.add(wnflist.get(i));
							json = CardUtil.send(li, list.get(0).getCardtypeid());
							logger.info("json:" + json);
						}
					}
					JSONObject jsonObject = JSONObject.fromObject(json);
					if (jsonObject.get("resultcode").equals("00")) {
						if(jsonObject.getJSONObject("responsedata").get("errorcode").equals("00")){
							logger.info("为用户"+list.get(0).getFrom_user_name()+"添加一张卡券;");
							logger.info("ID：" + wnflist.get(0).getGuid());
							//为用户添加一张卡券
							InsertUserCard(list.get(0).getFrom_user_name(),wnflist.get(0).getId()+"",orderinfo.getOrderid());
							//激活卡券
							EditCardState(wnflist.get(0).getId()+"");
						}
						else {
							logger.info("errorcode:"+jsonObject.getJSONObject("responsedata").get("errorcode"));
							j.setMsg("操作失败,卡券生成失败");
							return j;
						}
					}
					else {
						logger.info("resultcode:" + jsonObject.get("resultcode"));
						j.setMsg("操作失败,卡券生成失败");
						return j;
					}
				}
			}
			
			//-----------------------发推文--------------------------------
			
			//获取用户id
			String username = list.get(0).getFrom_user_name();
			String productName = list.get(0).getProductName();
		    int hh = daysBetween(list.get(0).getCreatedate(),list.get(0).getUpddate());//获取相差的小时数
		    String memo = "";
		    String tokenStr=orderInfoService.getToken("token");
			if (state.equals("0")) {
			    if (168 > hh) {
			    	memo = "您购买的商品:"+productName+"，已成功退货。由商家承担运费：您在“为你付”商城提出的订单：（"
			    		 + ""+orderinfo.getOrderid()+"）退货申请已确认，请将货品退回如下地址："+list.get(0).getRecvaddress()+"，"
			    		 + "联系人："+list.get(0).getLinkman()+",联系电话："+list.get(0).getPhone()+"。"
			    		 + "（由于此次退货申请在7天无理由退货日期内，运费将由卖家承担）";
				}
			    else {
					memo = "买家承担运费：您在“为你付”商城提出的订单：（"+orderinfo.getOrderid()+"）退货申请已确认，"
						 + "请将货品退回如下地址："+list.get(0).getRecvaddress()+"，"
						 + "联系人："+list.get(0).getLinkman()+",联系电话："+list.get(0).getPhone()+"。"
						 + "由于此次超过7天无理由退货日期，且退货申请非因商品质量问题造成，运费须由买家自行承担（不接受到付）。";
				}
			} else if (state.equals("1")) {
				memo = "您购买的商品:"+productName+"  退货失败，原因:"+remark+"。";
			} else if (state.equals("2")) {
				if (168 > hh) {
			    	memo = "您购买的商品:"+productName+"，已成功换货。由商家承担运费：您在“为你付”商城提出的订单：（"
			    		 + ""+orderinfo.getOrderid()+"）换货申请已确认，请将货品退回如下地址："+list.get(0).getRecvaddress()+"，"
			    		 + "联系人："+list.get(0).getLinkman()+",联系电话："+list.get(0).getPhone()+"。"
			    		 + "（由于此次换货申请在7天无理由退货日期内，运费将由卖家承担）";
				}
			    else {
					memo = "买家承担运费：您在“为你付”商城提出的订单：（"+orderinfo.getOrderid()+"）换货申请已确认，"
						 + "请将货品退回如下地址："+list.get(0).getRecvaddress()+"，"
						 + "联系人："+list.get(0).getLinkman()+",联系电话："+list.get(0).getPhone()+"。"
						 + "由于此次超过7天无理由退换货日期，且换货申请非因商品质量问题造成，运费须由买家自行承担（不接受到付）。";
				}
			} else if (state.equals("3")) {
				memo = "您购买的商品:"+productName+"  换货失败，原因:"+remark+"。";
			}
			WxToUserMessageUtil.toMassagesForUser(username,memo,tokenStr);
			//---------------------------发推文---------------------------
			//修改订单状态,更新库存
			logger.info("商品ID:"+list.get(0).getProduct_id());
			EditOrderInfo(id,status,remark);
			if (state.equals("0")) {
				EditProductInfo(list.get(0).getProduct_id(),orderinfo.getNum());
			}
			j.setMsg("操作成功");
			j.setSuccess(true);
			return j;
		} catch (Exception e) {
			logger.info("OrderInfoUpdateOrderState:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
			return j;
		}
	}
	
	/**
	 * 获取相差的小时数
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {   
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600);
        return Integer.parseInt(String.valueOf(between_days));
    }  
	
	/**
	 * 用户添加卡券
	 * @param userName
	 * @param id
	 * @param card
	 * @param orderid
	 */
	private void InsertUserCard(String userName, String id, String orderid){
		Usercard uc = new Usercard();
		uc.setFrom_user_name(userName);
		uc.setCard_id(id);
		uc.setState("0");
		uc.setCreatedate((new Date()));
		uc.setOrderid(orderid);
		orderInfoService.InsertUserCard(uc);
	}
	
	/**
	 * 激活卡券
	 * @param id
	 */
	private void EditCardState(String id){
		Card card = orderInfoService.getCard(Long.parseLong(id));
		card.setState(1);
		orderInfoService.EditCardState(card);
	}
	
	/**
	 * 修改订单状态
	 * @param id
	 * @param state
	 * @param remark
	 */
	private void EditOrderInfo(Long id, String state, String remark){
		Orderinfo orderinfo = orderInfoService.get(id);
		if (StringUtil.isEmpty(orderinfo.getLogistics()) && !orderinfo.getState().equals("04")) {
			state = "01";
		}
		orderinfo.setState(state);
		orderinfo.setUpddate((new Date()));
		orderinfo.setRemark(remark);
		orderInfoService.EditOrderInfo(orderinfo);
	}
	
	/**
	 * 修改商品库存
	 * @param id
	 */
	private void EditProductInfo(String id, int num){
		Product product = productService.get(Long.parseLong(id));
		String count = String.valueOf(Integer.parseInt(product.getNum())+num);
		product.setNum(count);
		productService.edit(product);
	}
}
