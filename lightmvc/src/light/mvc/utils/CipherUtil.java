package light.mvc.utils;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

/**
 * 加密解密、签名验签帮助类
 * 
 * @author lss
 * 
 */
public class CipherUtil {
	private final static Cipher cipher = new Cipher();
	private final static MessageDigest sign=new MessageDigest();
	
	/**
	 * 获取一个3des密钥
	 * 
	 * @author lss
	 * @date 2014-12-23
	 * @param password
	 *            密钥因子 长度不能小于24字节
	 * @return
	 */
	public static SecretKey getSecretKeyBy3DES(String password) {
		try {
			return CipherUtil.cipher.createSecretKey("DESede", password.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取一个3des密钥
	 * 
	 * @author lss
	 * @date 2014-12-23
	 * @param password
	 *            密钥因子 长度不能小于24字节
	 * @return
	 */
	public static SecretKey getSecretKeyBy3DES(byte[] password) {
		try {
			return CipherUtil.cipher.createSecretKey("DESede", password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	
	
	public static void main(String[] args) {
		Map<String, Key> map = new HashMap<String, Key>();
		map=getPairKeyByRSA("jt_test",1024);
		System.out.println(map.get("pub"));
		System.out.println();
		System.out.println(keyToString(map.get("pub")));
		System.out.println();
		System.out.println(keyToString(map.get("pri")));
		//StringUtil.decompress(strData)
		//private final static MessageDigest sign=new MessageDigest();
		/*try {
			System.out.println(CipherUtil.sign.sha1("a".getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	
	
	/**
	 * 获取一对RSA密钥
	 * 
	 * @author lss
	 * @date 2014-12-23
	 * @param password
	 *            密钥因子
	 * @return key=pub:公钥，key=pri:私钥
	 */
	public static Map<String, Key> getPairKeyByRSA(String password, int size) {
		try {
			return CipherUtil.cipher.createPairKey("RSA",StringUtil.isEmpty(password)?null:password.getBytes("UTF-8"), size);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将密钥转化为字符串
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param k
	 * @return
	 */
	public static String keyToString(Key k) {
		return CipherUtil.cipher.keyToStr(k);
	}

	/**
	 * 将字符串转换成RSA公钥
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param str
	 * @return
	 */
	public static PublicKey strRsaToPubKey(String str) {
		try {
			return (PublicKey) CipherUtil.cipher.strToKey(str, "RSA", "pub");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将字符串转换成RSA私钥
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param str
	 * @return
	 */
	public static PrivateKey strRsaToPriKey(String str) {
		try {
			return (PrivateKey) CipherUtil.cipher.strToKey(str, "RSA", "pri");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将字符串转换成3des密钥
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param str
	 * @return
	 */
	public static SecretKey str3DesToSecretKey(String str) {
		try {
			return (SecretKey) CipherUtil.cipher.strToKey(str, "DESede", "secret");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * MD5加密
	 * 
	 * @author lss
	 * @date 2014-12-23
	 * @param info
	 *            待加密字符串
	 * @param memcachePassword
	 *            加密因子
	 * @return
	 */
	public static String md5(String info) {
		try {
			return CipherUtil.sign.md5(info.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * sha1加密
	 * 
	 * @author lss
	 * @date 2014-12-29
	 * @param info
	 *            待加密字符串
	 * @param memcachePassword
	 *            加密因子
	 * @return
	 */
	public static String sha1(String info) {
		try {
			return CipherUtil.sign.sha1(info.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * sha1加密
	 * 
	 * @author lss
	 * @date 2014-12-29
	 * @param info
	 *            待加密字符串
	 * @param memcachePassword
	 *            加密因子
	 * @return
	 */
	public static String sha512(String info) {
		try {
			return CipherUtil.sign.sha512(info.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据密匙进行加密
	 * 
	 * @author lss
	 * @date 2014-7-31
	 * @param info 需加密信息
	 * @param key 密钥
	 * @param algorithm 加密方式:RSA/ECB/PKCS1Padding,DES/ECB/PKCS5Padding,DESede/ECB/PKCS5Padding,DESede/ECB/NoPadding
	 * @return
	 */
	public static String encrypt(byte[] info, Key key,String algorithm) {
		try {
			return CipherUtil.cipher.encrypt(info, key, algorithm);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 3DES加密
	 * 
	 * @author lss
	 * @date 2014-12-23
	 * @param info
	 *            待加密字符串
	 * @param memcachePassword
	 *            加密因子
	 * @return
	 */
	public static String DES3Encrypt(String info, SecretKey key) {
		try {
			return CipherUtil.cipher.encrypt(info.getBytes("UTF-8"), key, "DESede/ECB/PKCS5Padding");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 3des字符串密钥加密
	 * 
	 * @author lss
	 * @date 2014-12-29
	 * @param info
	 * @param key
	 *            字符串形式的密钥
	 * @return
	 */
	public static String DES3Encrypt(String info, String key) {
		try {
			return CipherUtil.DES3Encrypt(info, CipherUtil.str3DesToSecretKey(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据模数和公钥指数生产公钥
	 * @author lss
	 * @date 2015-12-1
	 * @param modulus
	 * @param exponent
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent){
		try {
			return CipherUtil.cipher.getPublicKey(modulus, exponent);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	/**
	 * 根据密匙进行解密
	 * 
	 * @author lss
	 * @date 2014-7-31
	 * @param info
	 * @param key
	 * @param algorithm
	 *            加密方式:RSA/ECB/PKCS1Padding,DES/ECB/PKCS5Padding,DESede/ECB/PKCS5Padding,DESede/ECB/NoPadding
	 * @return
	 */
	public static byte[] decrypt(String info, Key key,String algorithm) {
		try {
			return CipherUtil.cipher.decrypt(info, key, algorithm);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 3DES解密
	 * 
	 * @author lss
	 * @date 2014-12-23
	 * @param info
	 *            待解密字符串
	 * @param memcachePassword
	 *            解密因子
	 * @return
	 */
	public static String DES3Decrypt(String info, SecretKey key) {
		try {
			return new String(CipherUtil.cipher.decrypt(info, key, "DESede/ECB/PKCS5Padding"),"UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 3DES字符串密钥解密
	 * 
	 * @author lss
	 * @date 2014-12-23
	 * @param info
	 *            待解密字符串
	 * @param memcachePassword
	 *            解密因子
	 * @return
	 */
	public static String DES3Decrypt(String info, String key) {
		try {
			return CipherUtil.DES3Decrypt(info, CipherUtil.str3DesToSecretKey(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA加密
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param info
	 * @param key
	 * @return
	 */
	public static String RSAEncrypt(String info, Key key) {
		try {
			return CipherUtil.cipher.encrypt(info.getBytes("UTF-8"), key, "RSA/ECB/PKCS1Padding");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * RSA加密
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param info
	 * @param key
	 * @return
	 */
	public static String RSAEncrypt(String info, Key key,String algorithm) {
		try {
			return CipherUtil.cipher.encrypt(info.getBytes("UTF-8"), key, algorithm);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA私钥字符串加密
	 * 
	 * @author lss
	 * @date 2014-12-29
	 * @param info
	 * @param key
	 * @return
	 */
	public static String RSAEncryptPriKey(String info, String key) {
		try {
			return CipherUtil.RSAEncrypt(info, CipherUtil.strRsaToPriKey(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA公钥字符串加密
	 * 
	 * @author lss
	 * @date 2014-12-29
	 * @param info
	 * @param key
	 * @return
	 */
	public static String RSAEncryptPubKey(String info, String key) {
		try {
			return CipherUtil.RSAEncrypt(info, CipherUtil.strRsaToPubKey(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA解密
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param info
	 * @param key
	 * @return
	 */
	public static String RSADecrypt(String info, Key key) {
		try {
			return new String(CipherUtil.cipher.decrypt(info, key, "RSA/ECB/PKCS1Padding"),"UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA私钥字符串解密
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param info
	 * @param key
	 * @return
	 */
	public static String RSADecryptPriKey(String info, String key) {
		try {
			return CipherUtil.RSADecrypt(info, CipherUtil.strRsaToPriKey(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA公钥字符串解密
	 * 
	 * @author lss
	 * @date 2014-12-26
	 * @param info
	 * @param key
	 * @return
	 */
	public static String RSADecryptPubKey(String info, String key) {
		try {
			return CipherUtil.RSADecrypt(info, CipherUtil.strRsaToPubKey(key));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 字符串转换为16进制
	 * 
	 * @author lss
	 * @date 2015-1-5
	 * @param str
	 * @return
	 */
	public static String strToHex(String str) {
		try {
			return StringUtil.byte2hex(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 16进制字符串还原成正常字符串
	 * 
	 * @author lss
	 * @date 2015-1-5
	 * @param str
	 * @return
	 */
	public static String hexToStr(String hex) {
		try {
			return new String(StringUtil.hex2byte(hex), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
	}
}
