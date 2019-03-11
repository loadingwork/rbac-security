package com.iveiv.rbac.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.iveiv.rbac.domain.po.DictCategoryPO;

/**
 * 数据类型持久化
 * @author irays
 *
 */
public interface DictCategoryDAO extends JpaRepository<DictCategoryPO, Long>, JpaSpecificationExecutor<DictCategoryPO> {
	
	
	
	/**
	 * 根据pid获取数据
	 * @param pcode  父级编码
	 * @return
	 */
	List<DictCategoryPO>  findByPcode(String pcode);
	
	/**
	 * 根据编码获取数据
	 * @param code  编码
	 * @return
	 */
	DictCategoryPO  findByCode(String code);
	
	
	/**
	 *  模糊查询   
	 *  pcodes = %pcodes%
	 * @param pcodes
	 * @return
	 */
	List<DictCategoryPO>  findByPcodesContaining(String pcodes);
	
	/**
	 * 模糊查询  pcodes = pcodes
	 * @param pcodes
	 * @return
	 */
	List<DictCategoryPO>  findByPcodesLike(String pcodes);
	/**
	 * 根据pcodes查询
	 * @param pcodes
	 * @return
	 */
	List<DictCategoryPO>  findByPcodes(String pcodes);
	
	
	
	
	
	

}
