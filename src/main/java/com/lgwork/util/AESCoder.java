package com.lgwork.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * aes加密工具类
 * 
 * @author 梁栋, irays
 * @version 1.0
 */
public class AESCoder {
	
	/**
	 * 密钥算法
	 */
	public static final String KEY_ALGORITHM = "AES";

	/**
	 * 加密/解密算法 / 工作模式 / 填充方式 
	 * Java 6支持PKCS5Padding填充方式 
	 * Bouncy Castle支持PKCS7Padding填充方式
	 */
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 转换密钥
	 * 
	 * @param key 二进制密钥
	 * @return Key 密钥
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {

		// 实例化AES密钥材料
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {

		// 还原密钥
		Key k = toKey(key);

		/*
		 * 实例化 
		 * 使用PKCS7Padding填充方式，按如下方式实现 
		 * Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);

		// 执行操作
		return cipher.doFinal(data);
	}
	
	
	/**
	 * 解密
	 * @param base64Data  base64数据
	 * @param base64key  base64密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptToString(String base64Data, String base64key) throws Exception {
		return decryptToString(base64Data, Base64.decodeBase64(base64key));
	}
	
	/**
	 * 解密
	 * @param base64Data  base64数据
	 * @param key  二进制密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptToString(String base64Data, byte[] key) throws Exception {
		  byte[] decrypt = decrypt(Base64.decodeBase64(base64Data), key);
		  return new String(decrypt, StandardCharsets.UTF_8);
	}
	

	/**
	 * 加密
	 * 
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {

		// 还原密钥
		Key k = toKey(key);

		/*
		 * 实例化 
		 * 使用PKCS7Padding填充方式，按如下方式实现
		 * Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		 */
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);

		// 执行操作
		return cipher.doFinal(data);
	}
	
	
	/**
	 * aes加密得到base64信息
	 * 
	 * @param data  原始数据
	 * @param key 加密key
	 * @return  base64编码信息
	 * @throws Exception
	 */
	public static String encryptToBase64(String data, byte[] key) throws Exception {
		if(StringUtils.isEmpty(data)) {
			throw new RuntimeException("加密信息data为空");
		}
		byte[] encrypt = encrypt(data.getBytes(StandardCharsets.UTF_8), key);
		
		// 使用base64编码
		return Base64.encodeBase64String(encrypt);
	}
	
	/**
	 * aes加密得到base64信息
	 * @param data
	 * @param base64Key
	 * @return
	 * @throws Exception
	 */
	public static String encryptToBase64(String data, String base64Key) throws Exception {
		return encryptToBase64(data, Base64.decodeBase64(base64Key));
	}
	
	/**
	 * 默认初始化密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
		return initKey(128);
	}
	

	/**
	 * 生成密钥 <br>
	 * 
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey(int length) throws Exception {

		// 实例化
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

		/*
		 * AES 要求密钥长度为 128位、192位或 256位
		 */
		kg.init(length);

		// 生成秘密密钥
		SecretKey secretKey = kg.generateKey();

		// 获得密钥的二进制编码形式
		return secretKey.getEncoded();
	}

}
