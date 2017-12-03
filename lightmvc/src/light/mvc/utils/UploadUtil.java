/**   
 * This class is used for 文件上传至资源库
 * @author 韩逸
 * @version   
 *       1.0, 2016-7-21 
 */   

package light.mvc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	Logger logger = Logger.getLogger(UploadUtil.class.getName());
	/**
	 *@param 文件名  
	 *@return 上传后文件名。
	 */
	public String upload(MultipartFile multipartFile,HttpServletRequest request,String code)
	{   
		
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
        String data=dateformat.format(new Date());	
        //构建图片保存的目录
        String logoPathDir =SystemParam.uploadpath;
		 // 获取文件名/
        String filename = multipartFile.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        if (index == -1) {
                filename = null;
        } else {
                filename =code+data+filename.substring(index);
        }
       //拼成完整的文件保存路径加文件 
        String fileName = logoPathDir + File.separator + filename;
        if(filename!=null)
        {
        	File file = new File(fileName);
        	try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
        }
        return filename;
	}

	public static boolean download(String path, HttpServletResponse response) {
		try {  
            // path是指欲下载的文件的路径。  
            File file = new File(path);  
            // 取得文件名。  
            String filename = file.getName();  
            // 以流的形式下载文件。  
            InputStream fis = new BufferedInputStream(new FileInputStream(path));  
            byte[] buffer = new byte[fis.available()];  
            fis.read(buffer);  
            fis.close();  
            // 清空response  
            response.reset();  
            // 设置response的Header  
            response.addHeader("Content-Disposition", "attachment;filename="  
                    + new String(filename.getBytes()));  
            response.addHeader("Content-Length", "" + file.length());  
            OutputStream toClient = new BufferedOutputStream(  
                    response.getOutputStream());  
            response.setContentType("application/vnd.ms-excel;charset=gb2312");  
            toClient.write(buffer);  
            toClient.flush();  
            toClient.close();  
            return true;
        } catch (IOException ex) {  
            ex.printStackTrace();  
            return false;
        }  
    } 
	

	
	/**
	 *@param 上传文件到微商城并复制图片到管理平台
	 *@return 
	 */
	public String uploadManage(MultipartFile multipartFile,HttpServletRequest request,String code)
	{   
		
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String data=dateformat.format(new Date());
        //构建商城图片保存的目录
        String logoPathDir =SystemParam.uploadpath;
        //构建管理平台图片保存的目录
        String managePathDir =SystemParam.uploadmanagepath;
		 // 获取文件名/
    	String filename = multipartFile.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            filename = null;
        } else {
            filename =code+data+filename.substring(index);
        }
       //拼成完整的文件保存路径加文件 
        String fileName = managePathDir + File.separator + filename;
        if(filename!=null)
        {
        	File file = new File(fileName);
        	try {
				multipartFile.transferTo(file);
				copyFile(fileName,logoPathDir + File.separator + filename);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
        }
        return filename;
	}
	
	public void copyFile(String oldPath, String newPath) throws IOException {
	    try {
	        String newPathFolder = newPath.substring(0, newPath.lastIndexOf(File.separator));
	        File folder = new File(newPathFolder);
	        if (!folder.exists()){
	            folder.mkdirs();
	        }
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                 InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                 FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
             }
	    } catch (IOException e) {
	        throw new IOException(e);
	    }
	    
	}
	
	public String OpenTxt(String filePath) {  
		try {
            String encoding="UTF8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                java.io.BufferedReader bufferedReader = new  java.io.BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    return lineTxt;
                }
                read.close();
                return "";
		    }else{
		        logger.info("找不到指定的文件");
		        return "99";
		    }
	    } catch (Exception e) {
	        logger.info("读取文件内容出错");
	        return "99";
	    }
    }
}
