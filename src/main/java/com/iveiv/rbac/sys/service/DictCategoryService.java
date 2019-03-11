package com.iveiv.rbac.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.domain.po.DictCategoryPO;
import com.iveiv.rbac.domain.vo.ZtreeNodeVO;

/**
 * 数据字典分类服务接口
 * @author irays
 *
 */
public interface DictCategoryService {

	
	/**
	 * 分页搜索数据
	 * @param pageNum
	 * @param pageSize
	 * @param fgetDictCategoryPO
	 * @return
	 */
	Page<DictCategoryPO> pageSearchByCondition(Integer pageNum, Integer pageSize, DictCategoryPO fgetDictCategoryPO);

	/**
	 * 保存分类
	 * @param fgetDictCategoryPO
	 * @return
	 */
	BaseResult<String> saveDictCategoryPO(DictCategoryPO fgetDictCategoryPO) throws Exception;

	/**
	 * 根据id修改数据
	 * @param id
	 * @param fgetDictCategoryPO
	 * @return
	 */
	BaseResult<String> updateDictCategoryPO(Long id, DictCategoryPO fgetDictCategoryPO);

	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	BaseResult<String> deleteById(Long id);
	
	/**
	 * 获取子节点数据
	 * @param pid
	 * @return
	 */
	BaseResult<List<ZtreeNodeVO>> listChildrenTnode(final String pcode);
	
	/**
	 * 获取所有字节点
	 * @param pcode
	 * @return
	 */
	BaseResult<List<ZtreeNodeVO>> listAllChildrenTnode(final String pcode);

	/**
	 * 检查是否第一次添加
	 * @return
	 */
	BaseResult<String> handleCheckFirstAdd();

	
	/**
	 * 根据code获取
	 * @param code
	 * @return
	 */
	BaseResult<DictCategoryPO> getDictCategoryByCode(String code);

	
	/**
	 * 根据code删除数据
	 * @param code
	 * @return
	 */
	BaseResult<String> deleteByCode(String code);

	/**
	 * 根据code修改
	 * @param code
	 * @param fgetDictCategoryPO
	 * @return
	 */
	BaseResult<String> updateByCode(final String code, DictCategoryPO fgetDictCategoryPO);
	
	

}
