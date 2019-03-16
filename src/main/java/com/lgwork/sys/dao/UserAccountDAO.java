package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lgwork.domain.po.UserAccountPO;

/**
 * 
 * 用户账号持久化接口
 * 
 * @author irays
 *
 */
public interface UserAccountDAO extends JpaRepository<UserAccountPO, Long>, JpaSpecificationExecutor<UserAccountPO> {
	
	
	
	/**
	 * 
	 * 根据编码查询
	 * 
	 * @param ucode
	 * @return
	 */
	UserAccountPO findByUcode(String ucode);
	
	/**
	 * 根据手机号查询
	 * 
	 * @param username
	 * @return
	 */
	UserAccountPO findByUsername(String username);
	/**
	 * 根据手机号查询
	 * @param phone
	 * @return
	 */
	UserAccountPO findByPhone(String phone);
	/**
	 * 根据邮箱查询
	 * @param email
	 * @return
	 */
	UserAccountPO findByEmail(String email);
	
	
	/**
	 * 根据openId获取用户信息
	 * @param thirdPartyOpenId
	 * @param thirdPartyOauthPlateform
	 * @return
	 */
	UserAccountPO  findByThirdPartyOpenIdAndThirdPartyOauthPlateform(String thirdPartyOpenId, String thirdPartyOauthPlateform);
	
	

}
