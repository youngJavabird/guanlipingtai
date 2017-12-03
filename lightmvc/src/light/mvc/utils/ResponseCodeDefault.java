package light.mvc.utils;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应码定义
 * @author wq
 * @date 2015-10-20
 */
public abstract class ResponseCodeDefault {
	public static final String SUCCESS="00";
	
	public static final String PARAM_ERROR_ISNULL_SERVICE="01";//PNP1:请求服务不能为空
	public static final String PARAM_ERROR_ISNULL_FORMID="02";//PNP2:平台编号不能为空
	public static final String PARAM_ERROR_ISNULL_CHAREST="03"; //PNP3:字符集不能为空
	public static final String PARAM_ERROR_ISNULL_SIGN="04";//PNA6:签名信息不能为空
	public static final String PARAM_ERROR_ISNULL_SIGNTYPE="05";//PNA7:签名方式不能为空
	public static final String PARAM_ERROR_ISNULL_SIGNKEYINDEX="06";//PNA8:签名密钥索引不能为空
	public static final String PARAM_ERROR_ISNULL_TERMINALID="07";//PNA9:终端号不能为空
	
	public static final String PARAM_ERROR_FORMAT_SIGN="08";//PFP0:签名信息错误
	public static final String PARAM_ERROR_FORMAT_SIGNTYPE="09";//PFP0:不支持的签名方式
	public static final String PARAM_ERROR_FORMAT_FORWARD_MERCHANT="10";//PFP2:未配置商户接入信息
	public static final String SYS_ERROR="11";//PFP2:系统位置异常
	public static final String SYS_PARAM_ERROR="12";//PFP2:系统参数异常
	public static final String PARAM_ERROR_OPERATION_ERROR="13";//PEO1:操作失败
	public static final String WX_REMONEY_ERROR="14";//PEO1:操作失败
	public static final String SYS_ERROR_TIMEOUT="15";
	/**
	 * 错误信息定义
	 */
	public static final Map<String, String> ERROR_INFO = new HashMap<String, String>();
	static{
		//---------------------成功类响应信息 begin----------------------------
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.SUCCESS, "成功!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_ISNULL_SERVICE, "请求服务不能为空!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_ISNULL_FORMID, "平台编号不能为空!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_ISNULL_CHAREST, "字符集不能为空!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_ISNULL_SIGN, "签名信息不能为空!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_ISNULL_SIGNTYPE, "签名方式不能为空!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_ISNULL_SIGNKEYINDEX, "签名密钥索引不能为空!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_ISNULL_TERMINALID, "终端号不能为空!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_FORMAT_SIGN, "签名信息错误!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_FORMAT_SIGNTYPE, "不支持的签名方式!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_FORMAT_FORWARD_MERCHANT, "未配置商户接入信息!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.SYS_ERROR, "系统未知异常!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.SYS_PARAM_ERROR, "系统参数异常!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.PARAM_ERROR_OPERATION_ERROR, "操作失败!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.WX_REMONEY_ERROR, "微信退款失败!");
		ResponseCodeDefault.ERROR_INFO.put(ResponseCodeDefault.SYS_ERROR_TIMEOUT, "网络繁忙，请稍后重试");
		
	}
}
