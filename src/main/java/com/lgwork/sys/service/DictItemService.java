package com.lgwork.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lgwork.base.result.BaseResult;
import com.lgwork.domain.po.DictItemPO;

/**
 * 数据字典值控制器
 * @author irays
 *
 */
public interface DictItemService {

	/**
	 * 分页搜索数据
	 * @param pageNum
	 * @param pageSize
	 * @param fgetDictItemPO
	 * @return
	 */
	Page<DictItemPO> pageSearchByCondition(Integer pageNum, Integer pageSize, DictItemPO fgetDictItemPO);

	/**
	 * 保存数据字典值
	 * @param fgetDictItemPO  数据字典持久化对象
	 * @param dictTypeCode    数据字典编码
	 * @return
	 */
	BaseResult<String> saveDictItemPO(DictItemPO fgetDictItemPO, String typeCode);

	/**
	 * 修改
	 * @param id
	 * @param fgetDictItemPO
	 * @return
	 */
	BaseResult<String> updateDictItemPO(final Long id, final DictItemPO fgetDictItemPO);

	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	BaseResult<String> deleteById(Long id);
	
	/**
	 * 获取对象
	 * @param typeCode  数据字典类型编码
	 * @param dictCode  数据字典项编码
	 * @return
	 */
	DictItemPO getByTypeCodeAndDictCode(String typeCode, String dictCode);
	
	/**
	 * 根据id获取结果
	 * @param id
	 * @return
	 */
	DictItemPO getById(Long id);
	
	/**
	 * 根据数据字典类型编码获取列表
	 * @param TypeCode
	 * @return
	 */
	List<DictItemPO> listByTypeCode(String typeCode);
	
	/**
	 * 更新缓存
	 * @param typeCode
	 * @param dictCode
	 * @param id
	 * @param dictItemPO
	 * @return
	 */
	DictItemPO updateCache(String typeCode, String dictCode, Long id, DictItemPO dictItemPO);

	/**
	 *  根据typeCode获取列表
	 * @param pageNum
	 * @param pageSize
	 * @param typeCode
	 * @return
	 */
	Page<DictItemPO> listDictItemPoByTypeCode(Integer pageNum, Integer pageSize, String typeCode);
	

}
