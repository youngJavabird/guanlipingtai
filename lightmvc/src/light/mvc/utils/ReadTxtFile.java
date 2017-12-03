package light.mvc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;



import java.util.ArrayList;
import java.util.List;



public class ReadTxtFile {
	  public static List readTxtFile(String filePath){
	        try {
	                String encoding="utf-8";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ //判断文件是否存在
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    List collect = new ArrayList();  
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                        System.out.println(lineTxt);
	                        String[] str=lineTxt.split(",");
	                        collect.add(str);
	                    }
	                    read.close();
	                    return collect;
	        }else{
	            System.out.println("找不到指定的文件");
	            return null; 
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	        }
	        return null; 
	  }
	  
//	  public static String txt2String(File file){
//	        StringBuilder result = new StringBuilder();
//	        try{
//	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
//	            String s = null;
//	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
//	                result.append(System.lineSeparator()+s);
//	            }
//	            br.close();    
//	        }catch(Exception e){
//	            e.printStackTrace();
//	        }
//	        return result.toString();
//	    }
//	  
//	  public static File multipartToFile(MultipartFile multfile) throws IOException {  
//	        CommonsMultipartFile cf = (CommonsMultipartFile)multfile;   
//	        //这个myfile是MultipartFile的  
//	        DiskFileItem fi = (DiskFileItem) cf.getFileItem();  
//	        File file = fi.getStoreLocation();  
//	        //手动创建临时文件  
////	        if(file.length() < CommonConstants.MIN_FILE_SIZE){  
////	            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") +   
////	                    file.getName());  
////	            multfile.transferTo(tmpFile);  
////	            return tmpFile;  
////	        }  
//	        return file;  
//	    } 
}
