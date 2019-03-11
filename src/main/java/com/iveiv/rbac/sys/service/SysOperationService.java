package com.iveiv.rbac.sys.service;

import org.springframework.data.domain.Page;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.domain.po.SysOperationPO;

/**
 * 权限操作服务接口
 * @author irays
 *
 */
public interface SysOperationService {

	
	/**
	 * 分页搜索
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysOperationPO
	 * @return
	 */
	Page<SysOperationPO> pageSearchByCondition(Integer pageNum, Integer pageSize, SysOperationPO fgetSysOperationPO);
	
	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	SysOperationPO getById(Long id);

	/**
	 * 保存数据
	 * @param fgetSysOperationPO  要保存的对象
	 * @return
	 */
	BaseResult<String> saveSysOperationPO(SysOperationPO fgetSysOperationPO);

	/**
	 * 根据id修改
	 * @param id
	 * @param fgetSysOperationPO  要修改的对象
	 * @return
	 */
	BaseResult<String> updateSysOperationPO(final Long id, final SysOperationPO fgetSysOperationPO);

	/**
	 * 根据id删除对象
	 * @param id
	 * @return
	 */
	BaseResult<String> deleteById(Long id);
	

}
