package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.QrcodeTokenLoginPO;

/**
 * qrcodeToken登录持久化
 * @author irays
 *
 */
public interface QrcodeTokenLoginDAO extends JpaRepository<QrcodeTokenLoginPO, Long> {
	
	/**
	 * 根据qrcodeToken获取数据
	 * @param qrcodeToken
	 * @return
	 */
	QrcodeTokenLoginPO  findByQrcodeToken(String qrcodeToken);
	

}
