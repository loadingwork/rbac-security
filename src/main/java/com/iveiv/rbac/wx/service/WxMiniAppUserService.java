package com.iveiv.rbac.wx.service;

import com.iveiv.rbac.api.domain.dto.LoginSuccessDTO;
import com.iveiv.rbac.api.domain.dto.RequestHeaderDTO;
import com.iveiv.rbac.base.result.BaseResult;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 
 * 小程序用户服务接口
 * 
 * @author irays
 *
 */
public interface WxMiniAppUserService {
	
	
	
	/**
	 * 小程序后台用户登录
	 * @param appid
	 * @param code
	 * @param headers
	 * @return
	 * @throws WxErrorException
	 * @throws Exception
	 */
	BaseResult<LoginSuccessDTO>  sysUserLogin(String appid, String code, RequestHeaderDTO headers) throws WxErrorException, Exception;
	
	
	
	

}
