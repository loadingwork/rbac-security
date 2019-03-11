package com.iveiv.rbac.api.service;

import com.iveiv.rbac.api.domain.dto.RequestHeaderDTO;
import com.iveiv.rbac.api.exceptions.BizException;
import com.iveiv.rbac.base.result.BaseResult;

/**
 * 
 * 记住密码服务接口
 * 
 * @author irays
 *
 */
public interface TokenRememberMeAuthService {

	
	/**
	 * 
	 * app自动登录
	 * 
	 * @param headers
	 * @param rememberMeValue
	 * @return
	 * @throws BizException
	 * @throws Exception
	 */
	BaseResult<String> autoTokenRememberMeLogin(RequestHeaderDTO headers, final String rememberMeValue)
			throws BizException, Exception;

	/**
	 * app自动刷新
	 * @param headers
	 * @param rememberMeValue
	 * @return
	 * @throws BizException
	 * @throws Exception
	 */
	BaseResult<String> autoTokenRememberMeRefresh(RequestHeaderDTO headers, final String rememberMeValue)
			throws BizException, Exception;

}
