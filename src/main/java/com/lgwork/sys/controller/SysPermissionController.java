package com.lgwork.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.PageResult;
import com.lgwork.domain.po.SysPermissionPO;
import com.lgwork.sys.service.SysPermissionService;

/**
 * 权限控制器
 * @author irays
 *
 */
@RequestMapping("/sys/permission")
@Controller
public class SysPermissionController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/permission/permission";
	
	
	/**
	 * 权限控制器
	 */
	@Autowired
	private SysPermissionService sysPermissionService;
	
	
	/**
	 *   首页
	 * @return
	 */
	@GetMapping("/index.do")
	public String index() {
		return BASE_PATH + "/index";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public BaseResult<String> delete(@PathVariable("id") Long id) {
		
		BaseResult<String> result =  sysPermissionService.deleteById(id);
		
		return result;
	}
	
	
	/**
	 * 分页实现
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysPermissionPO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<SysPermissionPO>> searchGetSysRole(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final SysPermissionPO fgetSysPermissionPO
			) {
		
		Page<SysPermissionPO> pageSearchByCondition = sysPermissionService.pageSearchByCondition(pageNum, pageSize, fgetSysPermissionPO);
		
		
		return new PageResult<SysPermissionPO>(pageSearchByCondition);
	}
	
	
//	select_type
	

}
