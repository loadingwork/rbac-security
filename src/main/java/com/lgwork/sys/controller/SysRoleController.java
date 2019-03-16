package com.lgwork.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgwork.base.constant.ErrorPageConst;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.PageResult;
import com.lgwork.domain.po.SysRolePO;
import com.lgwork.sys.service.SysRoleService;




/**
 * 用户角色控制器
 * @author irays
 *
 */
@RequestMapping("/sys/role")
@Controller
public class SysRoleController {
	
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/permission/role";
	
	
	/**
	 * 系统角色控制
	 */
	@Autowired
	private SysRoleService sysRoleService;
	
	
	
	/**
	 * 分页实现
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysRolePO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<SysRolePO>> searchGetSysRole(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final SysRolePO fgetSysRolePO
			) {
		
		Page<SysRolePO> pageSearchByCondition = sysRoleService.pageSearchByCondition(pageNum, pageSize, fgetSysRolePO);
		
		
		return new PageResult<SysRolePO>(pageSearchByCondition);
	}
	
	/**
	 *  首页
	 * @return
	 */
	@GetMapping("/index.do")
	public String index() {
		return BASE_PATH + "/index";
	}
	
	/**
	 * 去增加页面
	 * @return
	 */
	@GetMapping("/add.do")
	public String addAction() {
		return BASE_PATH + "/add";
	}
	
	/**
	 * 修改
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/edit.do")
	public String edit(ModelMap model, Long id) {
		
		SysRolePO sysRolePO = sysRoleService.getById(id);
		
		if(sysRolePO == null) {
			return ErrorPageConst.ERROR404;
		}
		
		model.addAttribute("model", sysRolePO);
		
		return BASE_PATH + "/edit";
	}
	
	
	
	/**
	 * 创建角色
	 * @param fgetSysRolePO
	 * @return
	 */
	@PostMapping("/")
	@ResponseBody
	public BaseResult<String> createSysRole(SysRolePO fgetSysRolePO) {
		return sysRoleService.saveSysRole(fgetSysRolePO);
	}
	
	
	
	/**
	 * 修改角色信息
	 * 
	 * @param id
	 * @param fgetSysRolePO
	 * @return
	 */
	@PutMapping("/{id}")
	@ResponseBody
	public BaseResult<String> updateSysRolePO(@PathVariable("id") Long id, 
			SysRolePO fgetSysRolePO) {
		
		BaseResult<String> result =  sysRoleService.updateSysRolePO(id, fgetSysRolePO);
		
		return result;
	}
	
	
	/**
	 * 
	 * 删除角色
	 * 
	 * @param id
	 * @param fgetSysRolePO
	 * @return
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public BaseResult<String> delete(@PathVariable("id") Long id) {
		
		BaseResult<String> result =  sysRoleService.deleteSysRoleById(id);
		
		return result;
	}
	

}
