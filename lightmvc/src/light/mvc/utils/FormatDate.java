/**   
 * This class is used for ...   
 * @author 龚小斌 
 * @version   
 *       1.0, 2014-6-4 下午02:56:06   
 */   

package light.mvc.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatDate {
	 public FormatDate() {

	    }

	 

	    // 格式化日期为字符串 "yyyy-MM-dd   hh:mm"

	    public String formatDateTime(Date basicDate, String strFormat) {

	        SimpleDateFormat df = new SimpleDateFormat(strFormat);

	        return df.format(basicDate);

	    }

	 

	    // 格式化日期为字符串 "yyyy-MM-dd   hh:mm"

	    public String formatDateTime(String basicDate, String strFormat) {

	        SimpleDateFormat df = new SimpleDateFormat(strFormat);

	        Date tmpDate = null;

	        try {

	            tmpDate = df.parse(basicDate);

	        } catch (Exception e) {

	            // 日期型字符串格式错误

	        }

	        return df.format(tmpDate);

	    }

	 

	    // 当前日期加减n天后的日期，返回String (yyyy-mm-dd)

	    public static String nDaysAftertoday(int n) {

	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	        Calendar rightNow = Calendar.getInstance();

	        // rightNow.add(Calendar.DAY_OF_MONTH,-1);

	        rightNow.add(Calendar.DAY_OF_MONTH, +n);

	        return df.format(rightNow.getTime());

	    }

	 

	    // 当前日期加减n天后的日期，返回String (yyyy-mm-dd)

	    public Date nDaysAfterNowDate(int n) {

	        Calendar rightNow = Calendar.getInstance();

	        // rightNow.add(Calendar.DAY_OF_MONTH,-1);

	        rightNow.add(Calendar.DAY_OF_MONTH, +n);

	        return rightNow.getTime();

	    }

	 

	    // 给定一个日期型字符串，返回加减n天后的日期型字符串

	    public String nDaysAfterOneDateString(String basicDate, int n) {

	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	        Date tmpDate = null;

	        try {

	            tmpDate = df.parse(basicDate);

	        } catch (Exception e) {

	            // 日期型字符串格式错误

	        }

	        long nDay = (tmpDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)

	                * (24 * 60 * 60 * 1000);

	        tmpDate.setTime(nDay);

	 

	        return df.format(tmpDate);

	    }

	 

	    // 给定一个日期，返回加减n天后的日期

	    public Date nDaysAfterOneDate(Date basicDate, int n) {

	        long nDay = (basicDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)

	                * (24 * 60 * 60 * 1000);

	        basicDate.setTime(nDay);

	 

	        return basicDate;

	    }

	 

	    // 计算两个日期相隔的天数

	    public int nDaysBetweenTwoDate(Date firstDate, Date secondDate) {

	        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));

	        return nDay;

	    }

	 

	    // 计算两个日期相隔的天数

	    public int nDaysBetweenTwoDate(String firstString, String secondString) {

	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	        Date firstDate = null;

	        Date secondDate = null;

	        try {

	            firstDate = df.parse(firstString);

	            secondDate = df.parse(secondString);

	        } catch (Exception e) {

	            // 日期型字符串格式错误

	        }

	 

	        int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));

	        return nDay;

	    }
	    /** 
	    * 根据指定格式的日期字符串获得日期 
	    * @param dateStr 日期字符串 格式如：(yyyy-MM-dd) 
	    * @return 日期 
	    */ 
	    public static Date getDateByString(String dateStr) { 
	    String[] elements = dateStr.split("-"); 
	    if (elements.length != 3) { 
	    return new Date(); 
	    } 

	    Calendar cal = Calendar.getInstance(); 
	    cal.set(Calendar.YEAR, Integer.parseInt(elements[0])); 
	    cal.set(Calendar.MONTH, Integer.parseInt(elements[1]) - 1); 
	    cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(elements[2])); 

	    return cal.getTime(); 
	    } 
	    /** 
	    * 方法名：getFullFormateString 
	    * 方法描述：获取包含小时、分钟、秒的时间字符串 "yyyy-MM-dd HH:mm:ss" 
	    * @param date 日期 
	    * @return 指定格式的日期字符串 
	    */ 
	    public static String getFullFormateString(Date date){ 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    return date == null ? "" : sdf.format(date); 
	    } 

	    /** 
	    * 
	    * 方法描述：获取当前日期包含小时、分钟、秒的时间字符串 "yyyy-MM-dd HH:mm:ss" 
	    * @param date 日期 
	    * @return 指定格式的日期字符串 
	    */ 
	    public static String getNowFullFormateString(){ 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    return sdf.format(new Date()); 
	    } 
	    /**
	     * 获取当前时间 
	     * 
	     * 
	     */
	    public static String getNowDate(){
	    	SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
	    	return sdf.format(new Date());
	    }

	    public static String getDate(String date){ 
		    SimpleDateFormat sdf = new SimpleDateFormat(date); 
		    return sdf.format(new Date()); 
		    }
	    
	    
	    public static String getTime(String user_time) {  
	    String re_time = null;  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    Date d;  
	    try {  
	      
	      
	    d = sdf.parse(user_time);  
	    long l = d.getTime();  
	    String str = String.valueOf(l);  
	    re_time = str.substring(0, 10);  
	      
	      
	    } catch (Exception e) {  
	    // TODO Auto-generated catch block  
	    e.printStackTrace();  
	    }  
	    return re_time;  
	    } 
	    public static void main(String[] args) {
	    	String time = "2010-12-08";  
	    	// 字符串=======>时间戳  
	    	String re_str = getTime(time);  
	    	System.out.println(re_str); 
		}
}
