package com.lgwork.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lgwork.domain.po.SysMenuPO;

/**
 * 系统菜单
 * @author irays
 *
 */
public interface SysMenuDAO  extends JpaRepository<SysMenuPO, Long>, JpaSpecificationExecutor<SysMenuPO> {
	
	
	/**
	 * 根据code查询
	 * @param code
	 * @return
	 */
	SysMenuPO findByCode(String code);
	
	/**
	 * 根据父编码查询
	 * @param pcode
	 * @return
	 */
	List<SysMenuPO> findByPcode(String  pcode);
	
	/**
	 * 根据模糊查询
	 * @param pcodes
	 * @return
	 */
	List<SysMenuPO> findByPcodesContaining(String  pcodes);
	
	/**
	 * 获取sort最大的数据
	 * @return
	 */
	SysMenuPO  findTopByOrderByMenuSortDesc();
	
	

}
