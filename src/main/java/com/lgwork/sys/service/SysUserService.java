package com.lgwork.sys.service;

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.PageResult;
import com.lgwork.domain.dto.SysUserInfoDTO;
import com.lgwork.domain.po.SysUserPO;

/**
 * 系统用户服务接口
 * @author irays
 *
 */
public interface SysUserService  {

	
	
	/**
	 * 
	 * 分页获取用户信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param frontSysUserInfoDTO
	 * @return
	 */
	PageResult<SysUserInfoDTO> pageSearchByCondition(Integer pageNum, Integer pageSize, final SysUserInfoDTO frontSysUserInfoDTO);
	
	/**
	 * 根据系统编码获取
	 * @param ucode
	 * @return
	 */
	SysUserPO getByUcode(String ucode);

	
	/**
	 * 根据ucode删除
	 * @param ucode
	 */
	void deleteSysUserByUcode(String ucode);

	
	/**
	 * 根据ucode修改
	 * @param ucode
	 * @param fgetSysUserPO
	 * @return
	 */
	BaseResult<String> updateSysUserByUcode(String ucode, SysUserPO fgetSysUserPO);

}
