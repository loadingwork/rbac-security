package com.iveiv.rbac.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.domain.po.SysMenuPO;
import com.iveiv.rbac.domain.vo.ZtreeNodeVO;

/**
 * 菜单服务接口
 * @author irays
 *
 */
public interface SysMenuService {

	
	/**
	 *  搜索po列表
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysMenuPO
	 * @return
	 */
	Page<SysMenuPO> pageSearchByCondition(Integer pageNum, Integer pageSize, SysMenuPO fgetSysMenuPO);

	
	/**
	 * 创建菜单
	 * @param fgetSysMenuPO
	 * @return
	 */
	BaseResult<String> saveSysMenuPO(SysMenuPO fgetSysMenuPO) throws Exception;


	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	BaseResult<String> deleteById(Long id);

	/**
	 * 根据父节点获取数据
	 * @param pcode
	 * @return
	 */
	BaseResult<List<ZtreeNodeVO>> listAllChildrenTnode(String pcode);
	
	/**
	 * 根据编码修改数据
	 * @param code  编码
	 * @param fgetSysMenuPO  要修改的数据
	 * @return
	 */
	BaseResult<String> updateByCode(final String code, SysMenuPO fgetSysMenuPO);


	/**
	 * 执行操作
	 * @return
	 */
	BaseResult<String> handleCheckFirstAdd();

}
