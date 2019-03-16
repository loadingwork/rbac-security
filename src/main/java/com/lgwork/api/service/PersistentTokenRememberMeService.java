package com.lgwork.api.service;

import com.lgwork.domain.po.PersistentTokenRememberMePO;

/**
 * 	统一持久化记住我凭证功能服务接口
 * @author irays
 *
 */
public interface PersistentTokenRememberMeService {
	
	
	/**
	 * 保存记住密码信息
	 * @param persistentTokenRememberMePO
	 * @return
	 */
	PersistentTokenRememberMePO savePersistentTokenRememberMePO(PersistentTokenRememberMePO persistentTokenRememberMePO);
	
	/**
	 * 根据储存编码获取数据
	 * @param pcode
	 * @return
	 */
	PersistentTokenRememberMePO getByPcode(String pcode);
	

}
