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
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

/**
 * 字符串处理工具
 * @author wq
 * @date 2015-8-17
 */
public class StringUtils {
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

	public static String signSHA512(String info) {
		try {
			return sha512(info.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean verifySignSHA1(String sign,String msg){
		if(sign.equals(signSHA512(msg))){
			return true;
		}
		return false;
	}
	

	public static String sha512(byte[] info) throws NoSuchAlgorithmException{
		byte[] digesta = null;
		// 得到一个SHA-1的消息摘要
		java.security.MessageDigest alga = java.security.MessageDigest.getInstance("SHA-512");
		// 密码算sha1摘要
		alga.update(info);
		// 得到该摘要
		digesta = alga.digest();
		// 将摘要转为字符串
		String rs = StringUtils.byte2hex(digesta);
		return rs;
	}
}
