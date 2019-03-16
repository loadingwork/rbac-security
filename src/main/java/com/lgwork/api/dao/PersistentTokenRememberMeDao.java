package com.lgwork.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgwork.domain.po.PersistentTokenRememberMePO;


/**
 * 
 * 持久化工具类
 * 
 * @author irays
 *
 */
public interface PersistentTokenRememberMeDao  extends JpaRepository<PersistentTokenRememberMePO, String> {
	
	
	/**
	 * 根据唯一编码获取信息
	 * @param deleted
	 * @param pcode
	 * @return
	 */
	PersistentTokenRememberMePO findByPcode(String pcode);
	

}
