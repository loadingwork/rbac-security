package com.iveiv.rbac.sys.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.base.result.BaseResultBuilder;
import com.iveiv.rbac.base.result.PageResult;
import com.iveiv.rbac.domain.dto.SysUserInfoDTO;
import com.iveiv.rbac.domain.po.SysUserPO;
import com.iveiv.rbac.sys.service.SysUserService;


/**
 * 系统账户控制器
 * @author irays
 *
 */
@RequestMapping("/sys/user")
@Controller
public class SysUserController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/user/sys";
	
	/**
	 * 系统账户
	 */
	@Autowired
	private SysUserService sysUserService; 
	
	
	
	@GetMapping("/index.do")
	public String index() {
		return BASE_PATH + "/index";
	}
	
	
	/**
	 * 分页搜索
	 * @param pageNum
	 * @param pageSize
	 * @param sysUserInfoDTO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<SysUserInfoDTO>> pageListUserAccount(
		 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
		 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
		 SysUserInfoDTO sysUserInfoDTO) {
		
		PageResult<SysUserInfoDTO> pageSearchByCondition = sysUserService.pageSearchByCondition(pageNum, pageSize, sysUserInfoDTO);
		
		return pageSearchByCondition;
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
	public String edit(ModelMap model, String ucode) {
		
		SysUserPO sysUserPO = sysUserService.getByUcode(ucode);
		if(sysUserPO == null) {
			return "/error/popup404";
		}
		
		// 设置模型
		model.addAttribute("sysUser", sysUserPO);
		model.addAttribute("ucode", ucode);
		
		
		return BASE_PATH + "/edit";
	}
	
	
	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{ucode}")
	@ResponseBody
	public BaseResult<String> deleteUserAccount(@PathVariable("ucode") String ucode) {
		
		if(StringUtils.isEmpty(ucode)) {
			return  new BaseResultBuilder<String>().fail("ucode不能为空").build();
		}
		
		// 根据id删除数据
		sysUserService.deleteSysUserByUcode(ucode);
		
		return  new BaseResultBuilder<String>().build();
	}
	
	
	
	/**
	 * 根据ucode修改用户信息
	 * @return
	 */
	@PostMapping("/{ucode}")
	@ResponseBody
	public BaseResult<String> updateSysUser(@PathVariable("ucode") String ucode, 
			SysUserPO sysUserPO) {
		
		
		BaseResult<String> result = 
				sysUserService.updateSysUserByUcode(ucode, sysUserPO);
		
		
		return result;
	}
	
	

}
