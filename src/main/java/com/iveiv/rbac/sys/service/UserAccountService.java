package com.iveiv.rbac.sys.service;

import org.springframework.data.domain.Page;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.domain.po.SysUserPO;
import com.iveiv.rbac.domain.po.UserAccountPO;

/**
 * 账号服务接口
 * @author irays
 *
 */
public interface UserAccountService {
	
	
	
	/**
	 * 通过条件自定义查询
	 * @param pageNum
	 * @param pageSize
	 * @param userAccount
	 * @return
	 */
	Page<UserAccountPO>  pageSearchByCondition(Integer pageNum, Integer pageSize, UserAccountPO userAccount);

	/**
	 * 删除账号
	 * @param id
	 * @return
	 */
	BaseResult<String> deleteUserAccount(Long id);

	/**
	 * 保存账号信息
	 * @param userAccountFromFront
	 * @return
	 */
	BaseResult<String> saveUserAccount(UserAccountPO userAccountFromFront);

	
	/**
	 * 修改数据
	 * @param id
	 * @param userAccountFromFront
	 * @return
	 */
	BaseResult<String> updateUserAccount(Long id, UserAccountPO userAccountFromFront);
	
	/**
	 * 根据id获取信息
	 * @param id
	 * @return
	 */
	UserAccountPO getById(Long id);

	/**
	 * 保存sys账户信息
	 * @param ucode
	 * @param frontSysUserPO
	 * @return
	 */
	BaseResult<String> saveSysUser(String ucode, SysUserPO frontSysUserPO);
	

}
