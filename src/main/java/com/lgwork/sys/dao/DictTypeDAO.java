package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lgwork.domain.po.DictTypePO;

/**
 * 数据字典持久化
 * @author irays
 *
 */
public interface DictTypeDAO extends JpaRepository<DictTypePO, Long>, JpaSpecificationExecutor<DictTypePO> {
	
	
	
	/**
	 * 根据编码获取
	 * @param dictTypeCode
	 * @return
	 */
	DictTypePO findByTypeCode(String typeCode);
	
	
	

}
