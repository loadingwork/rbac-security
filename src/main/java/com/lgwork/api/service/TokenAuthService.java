package com.lgwork.api.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lgwork.api.domain.dto.LoginSuccessDTO;
import com.lgwork.api.domain.dto.RequestHeaderDTO;
import com.lgwork.api.domain.dto.ScanQrcodeLoginServerDTO;
import com.lgwork.api.exceptions.BizException;
import com.lgwork.base.result.BaseResult;

/**
 * 
 *  token授权登录
 * 
 * @author irays
 *
 */
public interface TokenAuthService {
	
	
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @param headers
	 * @return
	 * @throws BizException
	 * @throws Exception
	 */
	BaseResult<LoginSuccessDTO> login(final String username, final String password, final boolean rememberMe, RequestHeaderDTO headers) throws BizException, Exception;
	
	/**
	 * 用户登出
	 * @param req
	 * @param resp
	 * @param requestBody
	 * @throws BizException
	 * @throws Exception
	 */
	void logout(HttpServletRequest req, HttpServletResponse resp, final String requestBody) throws BizException, Exception;
	
	
	/**
	 * token后台验证
	 * @param headers
	 * @param token
	 * @return
	 * @throws BizException
	 * @throws Exception
	 */
	BaseResult<String> autoVerifyToken(final RequestHeaderDTO headers, final String token) throws BizException, Exception;
	
	/**
	 * token自动刷新
	 * @param headers
	 * @param token
	 * @param rememberMeValue
	 * @return
	 * @throws BizException
	 * @throws Exception
	 */
	BaseResult<String> autoTokenRefresh(RequestHeaderDTO headers, String token, String rememberMeSecret) throws BizException, Exception;

	
	/**
	 *  登录
	 * @param headers
	 * @param token
	 * @param qrcodeToken
	 * @return
	 */
	BaseResult<ScanQrcodeLoginServerDTO> qrcodeLoginByScan(RequestHeaderDTO headers, final String token, final String qrcodeToken);
	
	
	/**
	 * 	扫码确认登录
	 * @param headers
	 * @param token
	 * @param qrcodeToken
	 * @return
	 */
	BaseResult<String>  qrcodeConfirmLogin(RequestHeaderDTO headers, final String token, final String qrcodeToken);
	

	
	

}
