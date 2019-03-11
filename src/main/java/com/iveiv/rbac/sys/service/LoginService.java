package com.iveiv.rbac.sys.service;


/**
 * 
 * 登录服务接口
 * 
 * @author irays
 *
 */
public interface LoginService {

	
	
	/**
	 * 创建qrcode登录token
	 * @return
	 */
	String createQrcodeToken();
	
	/**
	 * 获取状态
	 * @param qrcodeToken
	 * @return
	 */
	String getQrcodeTokenFromRedis(String qrcodeToken);
	
	/**
	 * 更新redis状态
	 * @param qrcodeToken
	 * @param status
	 */
	void updateQrcodeTokenToRedis(String qrcodeToken, String status);
	
	
	/**
	 * 扫码登录
	 * @param qrcodeToken
	 * @param clientId
	 * @param clientToken
	 * @return
	 */
	String extractUsernameByQrcodeToken(String qrcodeToken, String clientId, String clientToken);
	
	
	
}
