package light.mvc.utils;



import java.util.ResourceBundle;




/**
 * 
 * 
 * 配置文件静态化
 * @date 2015-9-10
 */
public class SystemParam {
		static ResourceBundle rb  = PropertiesUtil.getProperties("uploadurl");
		public static String uploadpath = PropertiesUtil.getString("upload.path",rb, "");
		public static String uploadmanagepath = PropertiesUtil.getString("manageupload.path",rb,"");
		public static void main(String[] args) {
			System.out.println(SystemParam.uploadmanagepath);
			System.out.println(SystemParam.uploadpath);
		}
		/**
		 * api
		 */
		public static String api_sha512key = PropertiesUtil.getString("api.sha512key",rb, "");
		public static String api_url = PropertiesUtil.getString("api.url",rb, "");
		

		
		public static String merchno = PropertiesUtil.getString("merchno",rb, "");
		public static String md5key = PropertiesUtil.getString("md5.key",rb, "");
		public static String serviceurl = PropertiesUtil.getString("service.url",rb, "");
		public static String cardAPIUrl = PropertiesUtil.getString("service.apiurl",rb, "");
		public static String partnerkey = PropertiesUtil.getString("wx.partnerkey", rb, "");
		public static String cert = PropertiesUtil.getString("wx.cert", rb, "");
		public static String certapp = PropertiesUtil.getString("wx.certapp", rb, "");
		public static String appid = PropertiesUtil.getString("wx.appid", rb, "");
		public static String appsecret = PropertiesUtil.getString("wx.appsecret", rb, "");
		public static String mch_id = PropertiesUtil.getString("wx.mch_id", rb, "");
		public static String APPmch_id = PropertiesUtil.getString("wx.APPmch_id", rb, "");
		public static String APPappid = PropertiesUtil.getString("wx.APPappid", rb, "");
		public static String APPappsecret = PropertiesUtil.getString("wx.APPappsecret", rb, "");
		public static String APPcert = PropertiesUtil.getString("wx.APPcert", rb, "");

		public static String cip = PropertiesUtil.getString("c.ip", rb, "");
		public static Integer cport = PropertiesUtil.getInt("c.port", rb,9527 );
		}
	
