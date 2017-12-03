package light.mvc.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法类
 * @author wq
 * @date 2015-9-15
 */
public class MessageDigest {
	/**
	 * 计算md5摘要
	 * @author wq
	 * @date 2015-9-10
	 * @param info
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String md5(byte[] info) throws NoSuchAlgorithmException{
		byte[] digesta = null;
		// 得到一个md5的消息摘要 算法
		java.security.MessageDigest alga = java.security.MessageDigest.getInstance("MD5");
		//算MD5摘要
		alga.update(info);
		digesta = alga.digest();
		// 将摘要转为字符串
		String rs = StringUtil.byte2hex(digesta);
		return rs;
	}
	
	/**
	 * 计算sha1摘要
	 * @author wq
	 * @date 2015-9-10
	 * @param info
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String sha1(byte[] info) throws NoSuchAlgorithmException{
		byte[] digesta = null;
		// 得到一个SHA-1的消息摘要
		java.security.MessageDigest alga = java.security.MessageDigest.getInstance("SHA-1");
		// 密码算sha1摘要
		alga.update(info);
		// 得到该摘要
		digesta = alga.digest();
		// 将摘要转为字符串
		String rs = StringUtil.byte2hex(digesta);
		return rs;
	}

	/**
	 * 计算sha512摘要
	 * @author wq
	 * @date 2015-9-10
	 * @param info
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String sha512(byte[] info) throws NoSuchAlgorithmException{
		byte[] digesta = null;
		// 得到一个SHA-1的消息摘要
		java.security.MessageDigest alga = java.security.MessageDigest.getInstance("SHA-512");
		// 密码算sha1摘要
		alga.update(info);
		// 得到该摘要
		digesta = alga.digest();
		// 将摘要转为字符串
		String rs = StringUtil.byte2hex(digesta);
		return rs;
	}
	
}
