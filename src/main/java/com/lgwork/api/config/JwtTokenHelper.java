package com.lgwork.api.config;

import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lgwork.api.domain.dto.JwtTokenDTO;
import com.lgwork.api.exceptions.BizException;
import com.lgwork.enums.TokenOptionEnum;

/**
 * 
 * token辅助类
 * 
 * @author irays
 *
 */
public class JwtTokenHelper {
	
	/**
	 * 发行方
	 */
//	private static final String PAYLOAD_ISSUER = "www.iveiv.com";
	/**
	 * 储存编码
	 */
	public static final String PAYLOAD_PCODE = "pcode";
	/**
	 *  产品编号
	 */
	public static final String PAYLOAD_PNUM = "pnum";
	
	/**
	 * token配置
	 */
	private final TokenAuthProperties tokenAuthProperties;
	
	public JwtTokenHelper(TokenAuthProperties tokenAuthProperties) {
		this.tokenAuthProperties = tokenAuthProperties;
	}
	
	

	/**
	 * 
	 * @param jwtTokenDTO   
	 * @param defaultdAppKey  设备编组
	 * @param subject  主题
	 * @param issuedAt  发布事件
	 * @return
	 * @throws BizException  业务异常
	 */
	public String jwtEncrypt(JwtTokenDTO jwtTokenDTO) throws BizException {
		
		
		try {
			
			if(jwtTokenDTO == null) {
				throw new RuntimeException("jwtTokenDTO not null");
			}
			
			// 获取密钥
			String tokenSecretKey = tokenAuthProperties.getTokenSecretKey();
			
			// 加密用户信息
			String authToken = JWT.create()
					.withIssuer(tokenAuthProperties.getIssuer())
					// 主题
					.withSubject(jwtTokenDTO.getTokenOptionEnum().value())
					.withAudience(jwtTokenDTO.getPnum())
					.withIssuedAt(jwtTokenDTO.getIssuedAt())
					.withExpiresAt(jwtTokenDTO.getExpiresAt())
					// 用户唯一标识(ucode)
					.withJWTId(jwtTokenDTO.getUcode())
					// 储存主键
					.withClaim(PAYLOAD_PCODE, jwtTokenDTO.getPcode())
					.withClaim(PAYLOAD_PNUM, jwtTokenDTO.getPnum())
					.sign(Algorithm.HMAC256(tokenSecretKey));
			
			
			return authToken;
			
		} catch (Exception e) {
			// token加密失败
			throw BizException.TOKEN_ENCRYPT_FAIL;
		}
	}
	
	/**
	 * 
	 * 验证成功并返回数据
	 * 
	 * @param jwtToken
	 * @param defaultdAppKey
	 * @return
	 * @throws JWTVerificationException
	 */
	public JwtTokenDTO jwtVerifyDecrypt(String token) throws BizException {
		
		if(StringUtils.isEmpty(token)){
			throw new RuntimeException("authToken 不能为空");
		}
		
		// 获取密钥
		String tokenSecretKey = tokenAuthProperties.getTokenSecretKey();
		
		
		// 构建验证器
		JWTVerifier verified = JWT.require(Algorithm.HMAC256(tokenSecretKey))
                .build();
		
		
		try {
			
			// 验证
			DecodedJWT verifyObj = verified.verify(token);
			
			// 构建返回对象
			JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
			
			jwtTokenDTO.setUcode(verifyObj.getId());
			jwtTokenDTO.setPcode(verifyObj.getClaim(PAYLOAD_PCODE).asString());
			jwtTokenDTO.setPnum(verifyObj.getClaim(PAYLOAD_PNUM).asString());
			
			jwtTokenDTO.setIssuedAt(verifyObj.getIssuedAt());
			jwtTokenDTO.setExpiresAt(verifyObj.getExpiresAt());
			jwtTokenDTO.setTokenOptionEnum(TokenOptionEnum.getEnum(verifyObj.getSubject()));
			
			
			return jwtTokenDTO;
			
		} catch (JWTDecodeException e) {
			// token解密失败
			throw BizException.TOKEN_FORMAT_DECODE_FAIL;
		} catch (AlgorithmMismatchException e) {
			// 缺失加密方式
			throw BizException.TOKEN_DECRYPT_MODE_LESS;
		} catch (SignatureVerificationException e) {
			// 验证签名失败
			throw BizException.TOKEN_VERIFY_SIGNATURE_FAIL;
		} catch (TokenExpiredException  e) {
			// token过期
			throw BizException.TOKEN_ALREADY_EXPIRE;
		} catch (InvalidClaimException e) {
			// payload解析错误
			throw BizException.TOKEN_DECODE_PAYLOAD;
		} catch (JWTVerificationException e) {
			// token验证失败
			throw BizException.TOKEN_DECRYPT_FAIL;
		}
		
	}
	
	
	/**
	 * 这个没有验证直接解密数据
	 * 
	 * 
	 * 
	 * @param jwtToken
	 */
	public JwtTokenDTO jwtDecode(String token) {
		
		if(StringUtils.isEmpty(token)){
			throw new RuntimeException("token 不能为空");
		}
		
		try {
			// 解码后对象
			DecodedJWT decodeObj = JWT.decode(token);
			
			// 构建返回对象
			JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
			
			jwtTokenDTO.setUcode(decodeObj.getId());
			jwtTokenDTO.setPcode(decodeObj.getClaim(PAYLOAD_PCODE).asString());
			jwtTokenDTO.setPnum(decodeObj.getClaim(PAYLOAD_PNUM).asString());
			
			jwtTokenDTO.setIssuedAt(decodeObj.getIssuedAt());
			jwtTokenDTO.setExpiresAt(decodeObj.getExpiresAt());
			jwtTokenDTO.setTokenOptionEnum(TokenOptionEnum.getEnum(decodeObj.getSubject()));
			
			return jwtTokenDTO; 
		} catch (JWTDecodeException e) {
			// token解密失败
			throw BizException.TOKEN_FORMAT_DECODE_FAIL;
		} catch (JWTVerificationException e) {
			// token验证失败
			throw BizException.TOKEN_DECRYPT_FAIL;
		}
		
	}
	


	public TokenAuthProperties getTokenAuthProperties() {
		return tokenAuthProperties;
	}
	
	
	
}
