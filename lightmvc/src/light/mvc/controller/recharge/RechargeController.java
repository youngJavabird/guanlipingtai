package light.mvc.controller.recharge;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import light.mvc.controller.activity.ActivityController;
import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.message.Message;
import light.mvc.service.activity.ActivityServiceI;
import light.mvc.service.message.MessageServiceI;
import light.mvc.utils.ReqJsonUtil;
import light.mvc.utils.RequestApi;
import light.mvc.utils.SocketUtil;
import light.mvc.utils.StringUtil;


@Controller
@RequestMapping("/recharge")
public class RechargeController extends BaseController{
	
	Logger logger = Logger.getLogger(ActivityController.class.getName());
	
	@Autowired
	private ActivityServiceI activityService;
	@Autowired
	private 	MessageServiceI messageService;
	private final String ENCODE = "UTF-8";
	private final String CONTENT_TYPE = "text/html;charset=UTF-8";
	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		List<Activity> list=activityService.getActive();
		request.setAttribute("list", list);
		return "/recharge/recharge";
	}
	
	@RequestMapping("/importFile")
	public String importFile(HttpServletRequest request,HttpServletResponse response) {
		    String msg  ="";
			String id = request.getParameter("id");
			String [] ids = id.split("-");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    	MultipartFile file=multipartRequest.getFile("file");
	    	List<Message> list =null;
	    	try {
	    		SaveFileFromInputStream(file.getInputStream(), request.getRealPath("/upload/"), "phone.xls");
	    		 list= readXls(request.getRealPath("/upload/")+"/phone.xls");  
			} catch (Exception e) {
				logger.info("文件格式错误或其他错误！错误信息："+e.getMessage());
				
			}
	    	
				try {
						for (int i = 0; i < list.size(); i++) {
													
							String userid=list.get(i).getPhone();
							
							try {
								//传入传出字符编码
							request.setCharacterEncoding(ENCODE);
							response.setContentType(CONTENT_TYPE);
							response.setCharacterEncoding(ENCODE);
							String result=ReqJsonUtil.reqJson("02", "调用失败"); 	
							JSONObject requestData = new JSONObject();
							requestData.put("transtype", "0066");//增加活跃度接口
							requestData.put("user_phone", userid);
							requestData.put("activecode", ids[0]);
							requestData.put("transdate",  ReqJsonUtil.gettransdate());
							logger.info("活动充值接口请求报文:"+requestData.toString());
							String repjsonstr=	SocketUtil.sendToCore2(requestData.toString());
							logger.info("活动充值接口返回报文:"+repjsonstr);
							JSONObject repjson = JSONObject.fromObject(repjsonstr);
							String resultcode=repjson.getString("retcode");
							if(resultcode.equals("00")){
								logger.info("活动充值成功");
								msg = "手机号为"+userid+"的用户操作成功！ ";
							}else{			
								String result1=repjson.getString("result");
								msg = "手机号为"+userid+"的用户"+result1;
							}
							}  catch (Exception e) {
								logger.info("活动充值异常:"+ReqJsonUtil.errInfo(e));
							
							
						}
					}
					
					
					
					request.setAttribute("msg", msg);
				} catch (Exception e) {
					logger.info("导入异常："+e.getMessage());
					request.setAttribute("msg", "文件不存在或数据格式异常！");
					e.printStackTrace();
					
				}
			
				//查询活动信息
				List<Activity> list2 = activityService.getActive();
				request.setAttribute("list", list2);
				
				return "/recharge/recharge";
	}
	public static void SaveFileFromInputStream(InputStream stream,String path,String savefile) throws IOException{     
        FileOutputStream fs=new FileOutputStream( path + "/"+ savefile);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0;
        while ((byteread=stream.read(buffer))!=-1)
        {
           bytesum+=byteread;
           fs.write(buffer,0,byteread);
           fs.flush();
        }
        fs.close();
        stream.close();     
}
	 /**  
     * 读取xls文件内容  
     *  
     * @return List<XlsDto>对象  
     * @throws IOException  
     *             输入/输出(i/o)异常  
     */ 
    public static List<Message> readXls(String file) throws IOException {  
        InputStream is = new FileInputStream(file);  
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);  
        Message xlsDto = null;  
        List<Message> list = new ArrayList<Message>();  
        // 循环工作表Sheet  
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {  
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);  
            if (hssfSheet == null) {  
                continue;  
            }  
            // 循环行Row  
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {  
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);  
                if (hssfRow == null) {  
                    continue;  
                }  
                xlsDto = new Message(); 
                
                HSSFCell xh = hssfRow.getCell(1);  
                if (xh != null && !xh.toString().equals("")) {  
                	 xlsDto.setPhone(getValue(xh).toString()); 
                }  else{
                //	logger.info("手机号为空，跳过");
                	 continue;  
                }
               
               
                HSSFCell name = hssfRow.getCell(2);  
                if (name != null) {  
                	 xlsDto.setName(getValue(name).toString()); 
                }  
               
                
                HSSFCell idcard = hssfRow.getCell(3);  
                if (idcard != null) {  
                	 xlsDto.setIdcard(getValue(idcard).toString()); 
                }  
               
                
                HSSFCell email = hssfRow.getCell(4);  
                if (email != null) {  
                	 xlsDto.setEmail(getValue(email).toString()); 
                }  
               
                
                HSSFCell money = hssfRow.getCell(5);  
                if (money != null) {  
                	xlsDto.setMoney(getValue(money).toString()); 
                }  
                
                       
              
                list.add(xlsDto);  
            }  
        }  
        return list;  
    }  
    /**  
     * 得到Excel表中的值  
     *  
     * @param hssfCell  
     *            Excel中的每一个格子  
     * @return Excel中每一个格子中的值  
     */ 
    @SuppressWarnings("static-access")  
    private static String getValue(HSSFCell hssfCell) { 
    	 DecimalFormat df = new DecimalFormat("#");
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {  
            // 返回布尔类型的值  
            return String.valueOf(hssfCell.getBooleanCellValue()).trim();  
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {  
            // 返回数值类型的值  
            return df.format(hssfCell.getNumericCellValue());  
        } else {  
            // 返回字符串类型的值  
            return String.valueOf(hssfCell.getStringCellValue()).toString();  
        }  
    }  
}