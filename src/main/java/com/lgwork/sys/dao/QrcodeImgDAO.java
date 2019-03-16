package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgwork.domain.po.QrcodeImgPO;

/**
 * qr持久化
 * @author irays
 *
 */
public interface QrcodeImgDAO extends JpaRepository<QrcodeImgPO, Long> {
	
	
	/**
	 * 根据code获取信息
	 * @param code
	 * @return
	 */
	QrcodeImgPO findByCode(String code);
	

}
