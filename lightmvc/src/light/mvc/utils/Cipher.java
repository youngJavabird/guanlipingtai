package light.mvc.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加密、解密类
 * @author wq
 * @date 2015-9-10
 */
public class Cipher {
	/**
	 * 创建对称密匙
	 * 
	 * @author wq
	 * @date 2014-7-31
	 * @param algorithm 加密算法,可用 DES,DESede
	 * @param b 密钥因子
	 *        要求：DES加密，长度为8字节，即64位（其中：56位有效位，8位校验位）
	 *             DESede加密，长度为24字节，即192位（其中：168位有效位，24位校验位）
	 * @return 密钥
	 * @throws InvalidKeyException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public SecretKey createSecretKey(String algorithm, byte[] b) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec spec=null;
		switch (algorithm) {
		case "DES":
			spec=new DESKeySpec(b);
			break;
		case "DESede":
			spec = new DESedeKeySpec(b);  
			break;
		default:
			//throw new AlgorithmTypeException("algorithm error must is DES  or DESede");
		}
		SecretKeyFactory.getInstance(algorithm);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(algorithm);  
        return keyfactory.generateSecret(spec); 
	}

	/**
	 * 创建非对称密钥
	 * 
	 * @author wq
	 * @date 2014-12-23
	 * @param algorithm
	 *            支持RSA DSA
	 * @param b 加密因子
	 * @size 密钥长度
	 * @return 公钥和私钥，key=pub是公钥，key=pri:是私钥
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException 
	 */
	public Map<String, Key> createPairKey(String algorithm, byte[] b,
			int size) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 根据特定的算法一个密钥对生成器
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(algorithm);
		if(b!=null){
			// 加密随机数生成器 (RNG)
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			// 重新设置此随机对象的种子
			random.setSeed(b);
			// 使用给定的随机源（和默认的参数集合）初始化确定密钥大小的密钥对生成器
			keygen.initialize(size, random);// 
		}else{
			keygen.initialize(size);
		}
		// 生成密钥组
		KeyPair keys = keygen.generateKeyPair();
		// 得到公匙
		PublicKey pubkey = keys.getPublic();
		// 得到私匙
		PrivateKey prikey = keys.getPrivate();
		Map<String, Key> key = new HashMap<String, Key>();
		key.put("pub", pubkey);
		key.put("pri", prikey);
		return key;
	}
	
	/**
	 * 将密钥转换为字符串
	 * @author wq
	 * @date 2015-9-10
	 * @param k
	 * @return 16进制格式的密钥
	 */
	public String keyToStr(Key k){
		byte [] b=k.getEncoded();
		return StringUtil.byte2hex(b);
	}
	
	/**
	 * 将由密钥转换的字符串再次转换成密钥
	 * @author wq
	 * @date 2014-12-26
	 * @param keyStr 由秘药转换成的字符串
	 * @param algorithm 支持类型：RSA,DES,DESede
	 * @param type pri：取私钥，pub：取公钥,secret:取对称密钥
	 * @return 公钥、私钥
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public Key strToKey(String keyStr,String algorithm,String type) throws NoSuchAlgorithmException, InvalidKeySpecException{
		Key key=null;
		byte[] b=StringUtil.hex2byte(keyStr);
		if("pri".equals(type)){
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			//取私钥
			PKCS8EncodedKeySpec pKCS8EncodedKeySpec =new PKCS8EncodedKeySpec(b);
	        key = keyFactory.generatePrivate(pKCS8EncodedKeySpec); 
		}else if("pub".equals(type)){
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm); 
			//取公钥
            X509EncodedKeySpec x509KeySpec2 = new X509EncodedKeySpec(b);
            key= keyFactory.generatePublic(x509KeySpec2); 
		}else if("secret".equals(type)){
			 key = new SecretKeySpec(b, algorithm);
		}
        return key;
	}
	
	/**
	 * 根据密匙进行加密
	 * 
	 * @author wq
	 * @date 2014-7-31
	 * @param info 需加密信息
	 * @param key 密钥
	 * @param algorithm 加密方式:RSA/ECB/PKCS1Padding,DES/ECB/PKCS5Padding,DESede/ECB/PKCS5Padding,DESede/ECB/NoPadding
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public String encrypt(byte[] info, Key key, String algorithm)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		// 定义要生成的密文
		byte[] cipherByte = null;
		// 得到加密/解密器
		javax.crypto.Cipher c1 = javax.crypto.Cipher.getInstance(algorithm);
		// 用指定的密钥和模式初始化Cipher对象
		// 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
		c1.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
		// 对要加密的内容进行编码处理,
		cipherByte = c1.doFinal(info);
		// 返回密文的十六进制形式
		return StringUtil.byte2hex(cipherByte);
	}
	
	/**
	 * 根据密匙进行解密
	 * 
	 * @author wq
	 * @date 2014-7-31
	 * @param info
	 * @param key
	 * @param algorithm
	 *            加密方式:RSA/ECB/PKCS1Padding,DES/ECB/PKCS5Padding,DESede/ECB/PKCS5Padding,DESede/ECB/NoPadding
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidAlgorithmParameterException 
	 */
	public byte[] decrypt(String info, Key key, String algorithm)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		byte[] cipherByte = null;
		// 得到加密/解密器
		javax.crypto.Cipher c1 = javax.crypto.Cipher.getInstance(algorithm);
		// 用指定的密钥和模式初始化Cipher对象
		c1.init(javax.crypto.Cipher.DECRYPT_MODE, key);
		// 对要解密的内容进行编码处理
		cipherByte = c1.doFinal(StringUtil.hex2byte(info));
		return cipherByte;
	}
	
	/**
	 * 根据模数和公钥指数生产公钥
	 * @author wq
	 * @date 2015-12-1
	 * @param modulus
	 * @param exponent
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public RSAPublicKey getPublicKey(String modulus, String exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger b1 = new BigInteger(modulus);
		BigInteger b2 = new BigInteger(exponent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}
}
