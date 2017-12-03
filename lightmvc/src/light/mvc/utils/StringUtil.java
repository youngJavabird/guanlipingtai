package light.mvc.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;



public class StringUtil {

	/**
	 * 格式化字符串
	 * 
	 * 例：formateString("xxx{0}bbb",1) = xxx1bbb
	 * 
	 * @param str
	 * @param params
	 * @return
	 */
	public static String formateString(String str, String... params) {
		for (int i = 0; i < params.length; i++) {
			str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
		}
		return str;
	}
	
	
	/**
	 * 邮箱校验正则表达�?
	 */
	private static final Pattern email_Pattern = Pattern
			.compile("^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	//匹配手机�?
	private static final String mobile_regex = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";//"^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$";
	//匹配数字
	private static final String money_format="^\\d*.*d*$";
	//单位为分的金额转换为�?,除以100
	private static final BigDecimal hundred=new BigDecimal("100");
	
	/**
	 * �?查一个字符串是否为合法的电子邮件地址
	 * @author wq
	 * @date 2015-8-17
	 * @param input
	 * @return true：合法的电子邮件，false:非法的电子邮�?
	 */
	public static final boolean isEmail(CharSequence input) {
		return email_Pattern.matcher(input).matches();
	}
	
	/**
	 * 判断字符串是否为�?
	 * @author wq
	 * @date 2015-8-17
	 * @param str
	 * @return true:字符串为空，false:字符串非�?
	 */
	public static boolean isEmpty(String...str) {
		boolean falg=false;
		 for(int i = 0; str != null && i < str.length; i++){
			 falg = ((str[i] == null) || (str[i].trim().length() == 0)
						|| "null".equals(str[i]) || "".equals(str[i]));
		 }
		return falg;
	}
	
	
	public static boolean isNotEmpty(String...str) {
		
		return !isEmpty(str);
	}
	
	public static void main(String[] args) {
		System.out.println(isEmpty("0",""));
	}
	public static boolean isEmpty(String str) {
		
		return  ((str == null) || (str.trim().length() == 0)
						|| "null".equals(str) || "".equals(str));
	}
	/**
	 * 判断字符串是否为�?
	 * @author wq
	 * @date 2015-8-17
	 * @param str
	 * @return false:字符串为空，false:字符串非�?
	 */
	public static boolean isNotEmpty(String str) {
		return StringUtil.isEmpty(str)?false:true;
	}
	
	/**
	 * 验证手机号是否合�?
	 * @author wq
	 * @date 2015-8-17
	 * @param value
	 * @return true:合法手机号，false:非法手机�?
	 */
	public static boolean isMobile(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		return value.matches(mobile_regex);
	}
	
	/**
	 * 将字符串转换�?16进制形式
	 * @author wq
	 * @date 2015-1-13
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String toHexString(String str) throws UnsupportedEncodingException{
		byte[] b=str.getBytes("utf-8");
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
		stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
		if (stmp.length() == 1) {
		hs = hs + "0" + stmp;
		} else {
		hs = hs + stmp;
		}
		}
		return hs.toUpperCase();
	}
	
	/**
	 * 前后位保留方�?
	 * @author wq
	 * @date 2015-1-29
	 * @param source 原字符串
	 * @param start 前端保留字符串数�?
	 * @param end 后端保留字符串数�?
	 * @param c 中间�?要替换的字符
	 * @return
	 */
	public static String replaceStr(String source,int start,int end,char c){
		StringBuffer sb=new StringBuffer();
		if(StringUtil.isEmpty(source)||source.length()<=start+end){
			return source;
		}
		sb.append(source.substring(0, start));
		for (int i = 0; i <source.length()-start-end; i++) {
			sb.append(c);
		}
		if(end<source.length()){
			sb.append(source.substring(source.length()-end));
		}
		
		return sb.toString();
	}
	
	/**
	 * 字符串之后填充字符达到指定长�?
	 * @author wq
	 * @date 2015-6-18
	 * @param src 原字符串
	 * @param length  字符串长�?
	 * @param b 填充字符
	 * @return
	 */
	public static String paddingAfter(String src,int length,String b){
		if(StringUtil.isEmpty(src)){
			src="";
		}
		StringBuffer sb=new StringBuffer(src);
		for (int i = length-src.length(); i > 0; i--) {
			sb.append(b);
		}
		return sb.toString();
	}
	
	/**
	 * 字符串之前填充字符达到指定长�?
	 * @author wq
	 * @date 2015-6-18
	 * @param src 原字符串
	 * @param length  字符串长�?
	 * @param b 填充字符
	 * @return
	 */
	public static String paddingBefore(String src,int length,String b){
		if(StringUtil.isEmpty(src)){
			src="";
		}
		StringBuffer sb=new StringBuffer();
		for (int i = length-src.length(); i > 0; i--) {
			sb.append(b);
		}
		sb.append(src);
		return sb.toString();
	}

	/**
	 * 压缩数据:用标准ZIP格式压缩报文后再对压缩后的内容进行Base64编码
	 * @author wq
	 * @date 2015-8-31
	 * @param strData
	 * @return
	 */
	public static String compress(String strData) {
		return new sun.misc.BASE64Encoder().encode(StringUtil.compresszip(strData));
	}
	
	/**
	 * 用zip格式压缩报文
	 * @author wq
	 * @date 2015-8-31
	 * @param str
	 * @return
	 */
	private static final byte[] compresszip(String str) {
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		java.util.zip.ZipOutputStream zout = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new java.util.zip.ZipOutputStream(out);
			zout.putNextEntry(new java.util.zip.ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressed;
	}
	
	/**
	 * 解压缩：对内容进行Base64解码后用ZIP格式解压报文
	 * @param strData
	 * @return
	 */
	public static String decompress(String strData) {
		String strRnt = strData;
		try {
			strRnt = decompresszip(new sun.misc.BASE64Decoder().decodeBuffer(strData));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strRnt;
	}
	
	/**
	 * ZIP格式解压报文
	 * @param compressed
	 * @return
	 */
	private static final String decompresszip(byte[] compressed) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			java.util.zip.ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	} 

	/**
	 * 将二进制转化�?16进制字符�?
	 * 
	 * @param b
	 *            二进制字节数�?
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0" + stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * �?16进制字符串转换为2进制
	 * 
	 * @author wq
	 * @date 2014-7-31
	 * @param hex
	 *            16进制字符�?
	 * @return
	 */
	public static byte[] hex2byte(String hex) {
		byte[] b = hex.getBytes();
		if (b.length % 2 != 0) {
			throw new IllegalArgumentException("长度不是偶数�?");
		}
		byte[] b2 = new byte[b.length / 2];
		for (int i = 0; i < b.length; i += 2) {
			// 在b中从第i个字符开始复制，复制2个字符到item
			String item = new String(b, i, 2);
			b2[i / 2] = (byte) Integer.parseInt(item, 16);

		}
		return b2;
	}

	/**
	 * 将对象写入指定的文件,�?要实现java.io.Serializable接口
	 * 
	 * @param file
	 *            指定写入的文�?
	 * @param objs
	 *            要写入的对象
	 * @throws IOException
	 */
	public static void objForFile(String file, Object... objs) throws IOException {
		ObjectOutputStream oos = null;
		try {
			FileOutputStream fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			for (int i = 0; i < objs.length; i++) {
				oos.writeObject(objs[i]);
			}
		} finally {
			oos.close();
		}
	}
	
	private static SimpleDateFormat simpdate=new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 将日期对象转换为标准字符串（yyyyMMddHHmmss�?
	 * @author wq
	 * @date 2015-10-29
	 * @param d
	 * @return
	 */
	public static String dateToString(Date d){
		return simpdate.format(d);
	}
	
	/**
	 * 返回在文件中指定位置的对�?
	 * 
	 * @param file
	 *            指定的文�?
	 * @param i
	 *            �?1�?�?
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object getObjByFile(String file, int i)
			throws ClassNotFoundException, IOException {
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			for (int j = 0; j < i; j++) {
				obj = ois.readObject();
			}
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 将byte转换为一个长度为8的byte数组，数组每个�?�代表bit
	 * @author wq
	 * @date 2015-9-10
	 * @param b
	 * @return
	 */
	public static byte[] byteToBitByt(byte b) {
		byte[] array = new byte[8];
		for (int i = 7; i >= 0; i--) {
			array[i] = (byte) (b & 1);
			b = (byte) (b >> 1);
		}
		return array;
	}

	/**
	 * 把byte转为字符串的bit
	 */
	public static String byteToBitStr(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
				+ (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
				+ (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
				+ (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
	}

	/**
	 * 身份证校验，15位身份证由于没有校验码，因此无法校验�?
	 * 
	 * @author wq
	 * @date 2014-7-28
	 * @param id
	 * @return true:身份证号码存在，false:不存在的身份证号�?
	 */
	public static boolean checkID(String id) {
		int[] W = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,5, 8, 4, 2, 1 };
		boolean rc = false;
		if (id == null) {
			return rc;
		}
		if (id.length() == 15) {
			Pattern pattern = Pattern.compile("[0-9]*");
			return pattern.matcher(id).matches();
		}
		if (id.length() == 18) {
			int nCount = 0;
			int nIdNum;
			for (int i = 0; i < 18; i++) {
				char c = id.charAt(i);
				if ((c == 'X') || (c == 'x')) {
					nIdNum = 10;
				} else if ((c <= '9') || (c >= '0')) {
					nIdNum = c - '0';
				} else {
					return rc;
				}
				nCount += nIdNum * W[i];
			}
			if ((nCount % 11) == 1) {
				rc = true;
			}
		}
		return rc;
	}

	/**
	 * 16进制转ascii�?
	 * @author wq
	 * @date 2015-9-22
	 * @param len
	 * @param data_in
	 * @param data_out
	 */
	public static void hex2Ascii(int len, byte data_in[], byte data_out[])
    {
        byte temp1[] = new byte[1];
        byte temp2[] = new byte[1];
        int i = 0;
        int j = 0;
        for(; i < len; i++)
        {
            temp1[0] = data_in[i];
            temp1[0] = (byte)(temp1[0] >>> 4);
            temp1[0] = (byte)(temp1[0] & 15);
            temp2[0] = data_in[i];
            temp2[0] = (byte)(temp2[0] & 15);
            if(temp1[0] >= 0 && temp1[0] <= 9)
                data_out[j] = (byte)(temp1[0] + 48);
            else
            if(temp1[0] >= 10 && temp1[0] <= 15)
                data_out[j] = (byte)(temp1[0] + 87);
            if(temp2[0] >= 0 && temp2[0] <= 9)
                data_out[j + 1] = (byte)(temp2[0] + 48);
            else
            if(temp2[0] >= 10 && temp2[0] <= 15)
                data_out[j + 1] = (byte)(temp2[0] + 87);
            j += 2;
        }

    }

	public static void ascii2Hex(int len, byte data_in[], byte data_out[])
    {
        byte temp1[] = new byte[1];
        byte temp2[] = new byte[1];
        int i = 0;
        for(int j = 0; i < len; j++)
        {
            temp1[0] = data_in[i];
            temp2[0] = data_in[i + 1];
            if(temp1[0] >= 48 && temp1[0] <= 57)
            {
                temp1[0] -= 48;
                temp1[0] = (byte)(temp1[0] << 4);
                temp1[0] = (byte)(temp1[0] & 240);
            } else
            if(temp1[0] >= 97 && temp1[0] <= 102)
            {
                temp1[0] -= 87;
                temp1[0] = (byte)(temp1[0] << 4);
                temp1[0] = (byte)(temp1[0] & 240);
            }
            if(temp2[0] >= 48 && temp2[0] <= 57)
            {
                temp2[0] -= 48;
                temp2[0] = (byte)(temp2[0] & 15);
            } else
            if(temp2[0] >= 97 && temp2[0] <= 102)
            {
                temp2[0] -= 87;
                temp2[0] = (byte)(temp2[0] & 15);
            }
            data_out[j] = (byte)(temp1[0] | temp2[0]);
            i += 2;
        }

    }

	/**
	 * 将金额的单位从分转换位元，保�?2位小数后四舍五入
	 * @author wq
	 * @date 2015-11-16
	 * @param amt 单位为分的金�?
	 * @param defaultValue 转换失败的默认�??
	 * @return 单位为元的金�?
	 */
	public static String formatAmt(String amt,String defaultValue){
		if(StringUtil.isEmpty(amt))return defaultValue;
		if(!amt.matches(money_format))return defaultValue;
		BigDecimal big=new BigDecimal(amt);
		return big.divide(StringUtil.hundred,2,BigDecimal.ROUND_HALF_UP).toString();
	}
	/**
	 * 将金额的单位从分转换位元，保�?2位小数后四舍五入,转换失败的默认�??0.00
	 * @author wq
	 * @date 2015-11-16
	 * @param amt 单位为分的金�?
	 * @return 单位为元的金�?
	 */
	public static String formatAmt(String amt){
		return formatAmt(amt,"0.00");
	}
	
	/**
	 * 将骆驼命名法修改为带下划线的命名（主要用于数据库表字段的转换�?
	 * @author wq
	 * @date 2015-11-24
	 * @param param
	 * @return
	 */
	public static String camel2underline(String param){  
        Pattern  p=Pattern.compile("[A-Z]");  
        if(param==null ||param.equals("")){  
            return "";  
        }
        StringBuilder builder=new StringBuilder(param);  
        Matcher mc=p.matcher(param);  
        int i=0;  
        while(mc.find()){  
            builder.replace(mc.start()+i, mc.end()+i, "_"+mc.group().toLowerCase());  
            i++;  
        }
        if('_' == builder.charAt(0)){  
            builder.deleteCharAt(0);  
        }  
        return builder.toString();  
    } 
	
	
	/**
	 * 通过流的形式获取请求参数
	 * 
	 * @author wq
	 * @date 2015-4-28
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getMessageByStream(HttpServletRequest request) {
		try {// request.getInputStream();
			ServletInputStream in = request.getInputStream();
			// 得到客户端请求字符串
			byte[] ms = new byte[0];// 客户端请求数据
			byte[] items = new byte[0];// 当前读取数据
			int itemLen = 1024;// 每次最大读取长度
			int len = 0;// 实际读取长度
			do {
				byte[] item = new byte[1024];// 本次读取数据
				len = in.read(item);// 本次读取数据大小
				if(len>0){
					System.out.println("len--------------"+len);
					ms = new byte[ms.length + len];// 客户端请求数据=上次读取数据+本次读取数据
					System.arraycopy(items, 0, ms, 0, items.length);
					System.arraycopy(item, 0, ms, items.length, ms.length);
					items = new byte[ms.length];
					System.arraycopy(ms, 0, items, 0, items.length);
				}
			} while (len == itemLen);
			return new String(ms,"UTF-8");
		} catch (IOException e) {
			return null;
		}
	}
    private static String sign(JSONObject message)
    {
        message.remove("sign");
        message.put("sign", "");
        String sign = CipherUtil.md5((new StringBuilder(String.valueOf(message.toString()))).append(SystemParam.md5key).toString());
        message.put("sign", sign.toLowerCase());
        return message.toString();
    }
	public static String sendToCore(JSONObject message){
		sign(message);
        String resultStr = null;
        SSocketClientImpl ssocket = new SSocketClientImpl(SystemParam.cip, SystemParam.cport, 60000);
        String r = null;
        try
        {
            r = ssocket.send(message.toString().getBytes("UTF-8"));
            System.out.println(message);
            resultStr = r;
        }
        catch(Exception e)
        {
        
        	return "{\"retcode\":"+ResponseCodeDefault.SYS_ERROR_TIMEOUT+",\"result\":"+ResponseCodeDefault.ERROR_INFO.get(ResponseCodeDefault.SYS_ERROR_TIMEOUT)+"}";
        }
      
	
        return resultStr;
}
	
}
