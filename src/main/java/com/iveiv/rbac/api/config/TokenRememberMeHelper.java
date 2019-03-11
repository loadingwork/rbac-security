package com.iveiv.rbac.api.config;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.iveiv.rbac.api.domain.dto.TokenRememberMeDTO;
import com.iveiv.rbac.api.exceptions.BizException;
import com.iveiv.rbac.util.AESCoder;
import com.iveiv.rbac.util.JsonBodyUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 记住我工具辅助类
 * 
 * @author irays
 *
 */
@Slf4j
public class TokenRememberMeHelper {
	
	
	/**
	 * token配置
	 */
	private final TokenAuthProperties tokenAuthProperties;
	
	public TokenRememberMeHelper(TokenAuthProperties tokenAuthProperties) {
		this.tokenAuthProperties = tokenAuthProperties;
	}
	
	
	/**
	 * 
	 * 加密
	 * 
	 * @param tokenRememberMeDTO
	 * @param defaultdAppKey
	 * @param subject
	 * @return
	 * @throws BizException
	 */
	public String encrypt(TokenRememberMeDTO tokenRememberMeDTO) throws BizException {
		
		if(tokenRememberMeDTO == null) {
			throw new RuntimeException("加密参数异常");
		}
		
		try {
			
			final String rememberMeSecretKey = tokenAuthProperties.getRememberMeSecretKey();
			
			String jsonTokenRememberMeDTO = JsonBodyUtils.toJSONString(tokenRememberMeDTO);
			
			// 使用aes算法加密
			byte[] encryptData = AESCoder.encrypt(
					jsonTokenRememberMeDTO.getBytes(StandardCharsets.UTF_8), Base64.decodeBase64(rememberMeSecretKey));
			
			String encryptToBase64 = Base64.encodeBase64URLSafeString(encryptData);
			
			// 返回数据
			return encryptToBase64;
		} catch (Exception e) {
			log.debug("TokenRememberMe加密失败:  {}", e.getMessage());
			throw BizException.TOKEN_REMEMBER_ME_ENCRYPT_FAIL;
		}
		
	}
	
	/**
	 *  是否过期
	 * @param tokenExpiryTime
	 * @return
	 */
	public boolean isTokenExpired(long tokenExpiryTime) {
		return tokenExpiryTime < System.currentTimeMillis();
	}
	
	/**
	 * 是否失效
	 * @param tokenExpiryTime
	 * @return
	 */
	public boolean isTokenInvalid(long tokenInvalidTime) {
		return tokenInvalidTime < System.currentTimeMillis();
	}
	
	public TokenRememberMeDTO decrypt(String rememberMeValue) throws BizException {
		
		if(StringUtils.isEmpty(rememberMeValue)) {
			throw new RuntimeException("rememberMeKey not null");
		}
		
		try {
			
			// 记住密码密钥
			final String rememberMeSecretKey = tokenAuthProperties.getRememberMeSecretKey();
			
			// 解密
			String jsonData = AESCoder.decryptToString(rememberMeValue, rememberMeSecretKey);
			
			TokenRememberMeDTO tokenRememberMeDTO = JsonBodyUtils.parseObject(jsonData, TokenRememberMeDTO.class);
				
			return tokenRememberMeDTO;
			
		} catch (Exception e) {
			log.debug("密钥解密失败: {}", rememberMeValue);
			throw BizException.TOKEN_REMEMBER_ME_DECRYPT_FAIL;
		}
		
	}
	
	
	

	public TokenAuthProperties getTokenAuthProperties() {
		return tokenAuthProperties;
	}
	
	

}
