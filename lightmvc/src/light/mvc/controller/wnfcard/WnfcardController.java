package light.mvc.controller.wnfcard;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.controller.goods.ProductController;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.sys.User;
import light.mvc.pageModel.wnfcard.Cardd;
import light.mvc.service.base.ServiceException;
import light.mvc.service.card.CardtypeServiceI;


import light.mvc.service.wnfcard.CarddServiceI;
import light.mvc.utils.FromExlsUtil;
import light.mvc.utils.ImportExcelUtil;
import light.mvc.utils.ResponseCodeDefault;
import light.mvc.utils.RsaUtil;
import light.mvc.utils.SSocketClientImpl;
import light.mvc.utils.StringUtil;
import light.mvc.utils.UploadUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;


















import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;







@Controller
@RequestMapping("/wnfcard")
public class WnfcardController extends BaseController {
	Logger logger = Logger.getLogger(ProductController.class.getName());
	@Autowired
	private CarddServiceI carddServiceI;

	@RequestMapping("/manager")
	public String manager() {
		return "/card/cardd";
	}
	@RequestMapping("/addPage")
	public String addPage() {
		return "/card/carddadd";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Cardd cardd, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(carddServiceI.dataGrid(cardd, ph));
		grid.setTotal(carddServiceI.count(cardd, ph));
		return grid;
	}
	private String uuid() {
		String uuid=UUID.randomUUID().toString();
		return uuid;
	}
	/**
	 * 导入els
	 * @throws InvalidAtributeException 
	 */
	@RequestMapping("add")
	@ResponseBody
	public Json fromExcel( HttpServletRequest request,HttpServletResponse response ) throws Exception{ 
		Json j = new Json();
		try {
	//	String file = request.getParameter("file");//卡券导入路径
	//	String fileimg = request.getParameter("fileimg");//图片路径000
	   // file="D:/55555555.xls";
    	//fileimg="D://barcodeimg/";
    	String card_name = request.getParameter("card_name");//卡券使用名称
    	//String card_picture = request.getParameter("card_picture");//卡券图标
    	String card_colour = request.getParameter("card_colour");//卡券左侧底色
    	//String card_explain = request.getParameter("card_explain");//卡券说明
    	String card_type = request.getParameter("card_type");//type
    	String card_service_condition_one = request.getParameter("card_service_condition_one");//卡券说明一
    	//String card_service_condition_two = request.getParameter("card_service_condition_two");//卡券说明二
    	String card_offset_description = request.getParameter("card_offset_description");//抵消说明
    	String card_detile = request.getParameter("card_detile");//卡券详细说明
    	String card_userid = request.getParameter("card_userid");//操作员代码
    	String type_id = request.getParameter("cardtypeid");
    	//String card_effecticeday = request.getParameter("card_effecticeday");//有效天数
    	//卡券导入
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	MultipartFile file=multipartRequest.getFile("card_file");
    	

        
        
    	List<Cardd> list =null;
    	try {
//    		SaveFileFromInputStream(file.getInputStream(), request.getRealPath("/upload/"), "kaquan.xls");
//    		 list=  FromExlsUtil.readXls(request.getRealPath("/upload/")+"/kaquan.xls");  
        	InputStream    in = file.getInputStream();  
        	List<List<Object>>  listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());  
            
            in.close();  
       
         list=new  ArrayList<Cardd>();
            //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出  
            for (int i = 0; i < listob.size(); i++) {  
                List<Object> lo = listob.get(i);  
            	if( listob.size()==0|| listob.size()==1){
            	     System.out.println(String.valueOf(lo.get(0)));
            	}
            
                Cardd vo = new Cardd();  
           
                vo.setCard_code(String.valueOf(lo.get(0)));  
                vo.setCard_seqno(String.valueOf(lo.get(1)));  
                vo.setCard_password(String.valueOf(lo.get(2)));  
                vo.setCard_type(String.valueOf(lo.get(3)));  
                vo.setCard_five(String.valueOf(lo.get(4))); 
                vo.setCard_price(String.valueOf(lo.get(5))); 
                vo.setCard_endtime(String.valueOf(lo.get(6))); 
                list.add(vo);
            }  
    		 Collections.shuffle(list);
		} catch (Exception e) {
			logger.info("文件格式错误或其他错误！！！！！错误信息：：：："+e.getMessage());
			e.printStackTrace();
		}
    	
        //导入图片
	     MultipartFile multipartFile = multipartRequest.getFile("card_picture");

	   	 UploadUtil up=new UploadUtil(); 
	   	 
	   	 //得到处理后的上传图片名称
	   	 //图片路径
	   	 String card_picture=up.uploadManage(multipartFile, multipartRequest,"card_picture");
        for(int i=0;i<list.size();i++){
        	String endtime=list.get(i).getCard_endtime();
            if(StringUtil.isNotEmpty(endtime)){
                String  password =list.get(i).getCard_password();
                
                try {
//                	password = RsaUtil.encrypt("/home/management/tomcat7/webapps/publicKey.keystore", password);
                	String req=sendToCore(password+",ENC,RSA_NO_PADDING,PUB");
                	String[] rpassword=new String[1];
                	rpassword=req.split(",");
                	if(rpassword[0].equals("00")){
                		password=rpassword[1];
                	}else{
                		password="";
                	}
    			} catch (Exception e) {
    				logger.info("密码加密异常！"+e.getMessage());			
    			}
                Cardd cardd=new Cardd();
                //判段是DQ券的时候不加密，另外的加密
                if(type_id.equals("1")||type_id.equals("2")||type_id.equals("3")||type_id.equals("4")){
                	cardd.setCard_code(list.get(i).getCard_code());
                }else{
                String code=list.get(i).getCard_code();
                try {
//                	code=RsaUtil.encrypt("/home/management/tomcat7/webapps/publicKey.keystore", code);
                	String res=sendToCore(code+",ENC,RSA_NO_PADDING,PUB");
                	String[] rcode=new String[1];
                	rcode=res.split(",");
                	if(rcode[0].equals("00")){
                		code=rcode[1];
                	}
    			} catch (Exception e) {
    				logger.info("卡號加密异常！"+e.getMessage());			
    			}
                cardd.setCard_code(code);
                }
                cardd.setGuid(uuid());
                cardd.setCard_name(card_name);
                cardd.setCard_picture(card_picture);
                cardd.setCard_password(password);
                cardd.setCard_type(card_type);
                cardd.setCard_colour(card_colour);
                cardd.setCard_bar_code("");
                cardd.setCard_price(list.get(i).getCard_price()); 
                
                cardd.setCard_endtime(list.get(i).getCard_endtime());
                cardd.setCard_service_condition_one(card_service_condition_one);
                cardd.setCard_offset_description(card_offset_description);
                cardd.setCard_detile(card_detile);
                cardd.setCard_userid(card_userid);
                cardd.setCard_seqno(list.get(i).getCard_seqno());
                cardd.setCard_five(list.get(i).getCard_five());
                cardd.setCard_typeid(list.get(i).getCard_type());
                cardd.setType_id(Integer.parseInt(type_id));

            	 carddServiceI.add(cardd);
            }
        
            	
            


           }
             j.setMsg("添加成功！");
   	         j.setSuccess(true); 	        
             }catch (Exception e) {
   		          logger.info("wnfcardAdd:"+e.getMessage());
   		           j.setMsg("系统异常,请联系管理员！");
         	}
                  	return j;
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
	
	public static String sendToCore(String message){
		
	        String resultStr = null;
	        SSocketClientImpl ssocket = new SSocketClientImpl("172.16.3.2", 9999, 60000);
//	        SSocketClientImpl ssocket = new SSocketClientImpl("192.168.2.5", 9999, 60000);
	        String r = null;
	        try
	        {
//	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSSS");
//	        	System.out.println(sdf.format(System.currentTimeMillis()));
	            r = ssocket.send(message.toString().getBytes("UTF-8"));
//	            System.out.println(sdf.format(System.currentTimeMillis()));
	            resultStr = r;
	        }
	        catch(Exception e)
	        {
	        	//log.info((new StringBuilder("sendToCore c error:")).append(e.getMessage()).toString(), e);
	        	return "{\"retcode\":"+ResponseCodeDefault.SYS_ERROR_TIMEOUT+",\"result\":"+ResponseCodeDefault.ERROR_INFO.get(ResponseCodeDefault.SYS_ERROR_TIMEOUT)+"}";
	        }
	        //log.info((new StringBuilder("sendTtoCore responset data:")).append(resultStr).toString());
		
	        return resultStr;
	}
	public static void main(String[] args) {
		String a =sendToCore("CoGe32csyKajJKUp+UdKBuVyVALyARUYdarqHUyv7jOp90SICc78ZKiBgeh/6NFmlqJdrepydIXTfrMW1Bi1j0ARNPvDMecpKA3HaMZLaOL+1AIALQqDxv8ZnktWs4by4Gzty5PZ1Gp+OGdfFRBmvgbmCAZLOK+2mwEQlnJ/CAA="+",DEC,RSA_NO_PADDING,PRI");
		System.out.println(a);
/*	 	String[] rcode=new String[1];
    	rcode=a.split(",");
    	System.out.println(rcode[0]);
     	System.out.println(rcode[1]);*/

	}
}
