package com.lgwork.sys.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lgwork.domain.po.DictItemPO;

/**
 * 数据字典项持久化
 * 
 * 注: 为了更好的使用缓存, 禁止DictItemService以外的地方操作数据
 * 
 * 注:  Repository
 * @author irays
 *
 */
public interface DictItemDAO extends JpaRepository<DictItemPO, Long>, JpaSpecificationExecutor<DictItemPO> {
	
	
	/**
	 * 根据code获取信息
	 * @param dictCode
	 * @return
	 */
	DictItemPO findByTypeCodeAndDictCodeAndEnabled(String typeCode, String dictCode, boolean enabled);
	/**
	 * 获取sort最大值
	 * @return
	 */
	DictItemPO findTopByOrderBySortDesc();
	
	/**
	 * 根据数据字典类型编码获取列表
	 * @param typeCode  数据字典类型编码
	 * @param enabled  是否使用
	 * @return
	 */
	List<DictItemPO> findByTypeCodeAndEnabled(String typeCode, boolean enabled);
	
	/**
	 * 根据typeCode获取信息
	 * @param typeCode
	 * @param pageable
	 * @return
	 */
	Page<DictItemPO>  findByTypeCode(String typeCode, Pageable pageable);
	

}
