package com.iveiv.rbac.sys.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.domain.po.DictTypePO;

/**
 * 数据字典服务接口
 * @author irays
 *
 */
public interface DictTypeService {

	/**
	 * 分页搜搜数据字典
	 * @param pageNum
	 * @param pageSize
	 * @param fgetDictTypePO
	 * @return
	 */
	Page<DictTypePO> pageSearchByCondition(Integer pageNum, Integer pageSize, DictTypePO fgetDictTypePO);

	/**
	 * 保存数据字典
	 * @param fgetDictTypePO
	 * @param dictCategoryCode
	 * @return
	 */
	BaseResult<String> saveDictTypePO(DictTypePO fgetDictTypePO, String dictCategoryCode)  throws Exception;

	/**
	 * 根据id修改数据
	 * @param id
	 * @param fgetDictTypePO
	 * @return
	 */
	BaseResult<String> updateDictTypePO(final DictTypePO fgetDictTypePO);

	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	BaseResult<String> deleteById(Long id);
	
	
	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	Optional<DictTypePO> getById(Long id);

}
