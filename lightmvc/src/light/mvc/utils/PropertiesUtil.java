package light.mvc.utils;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Properties文件解析
 * @author wq
 *
 */
public class PropertiesUtil {
	/**
	 * 根据文件名，创建ResourceBundle对象
	 * @param fileName
	 * @return
	 */
	public static ResourceBundle getProperties(String fileName){
		return ResourceBundle.getBundle(fileName, Locale.getDefault());
	}
	
	/**
	 * 根据键获取对应的�??
	 * @author wq
	 * @date 2015-10-28
	 * @param key
	 * @param rb
	 * @param defaultValue  默认�??
	 * @return
	 */
	public static Object getObject(String key, ResourceBundle rb,
			Object defaultValue){
		Object value = null;
		if (rb.containsKey(key)) {
			value = rb.getObject(key);
		}
		
		return value==null? defaultValue : value;
	}
	
	/**
	 * 根据键获取对应的�??
	 * @param key
	 * @param rb
	 * @param defaultValue 默认�??
	 * @return
	 */
	public static String getString(String key, ResourceBundle rb,
			String defaultValue) {
		String value = "";
		if (rb.containsKey(key)) {
			value = (String) PropertiesUtil.getObject(key, rb,defaultValue);
		}
		return StringUtil.isEmpty(value) ? defaultValue : value;
	}
	
	/**
	 * 根据键获取对应的�??
	 * @param key
	 * @param rb
	 * @param defaultValue 默认�??
	 * @return int类型的数�??
	 */
	public static int getInt(String key, ResourceBundle rb, int defaultValue) {
		if (rb.containsKey(key)) {
			int value = Integer.parseInt((String)PropertiesUtil.getObject(key, rb,defaultValue));
			return value;
		}
		return defaultValue;
	}
	
	/**
	 * 根据键获取对应的�??
	 * @param key
	 * @param rb
	 * @param defaultValue 默认�??
	 * @return boolean类型的数�??
	 */
	public static boolean getBoolean(String key, ResourceBundle rb, boolean defaultValue) {
		if (rb.containsKey(key)) {
			if("true".equals((String)PropertiesUtil.getObject(key, rb,defaultValue))){
				return true;
			}
			return false;
		}
		return defaultValue;
	}
}
