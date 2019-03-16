package com.lgwork.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.base.result.PageResult;
import com.lgwork.domain.po.SysUserPO;
import com.lgwork.domain.po.UserAccountPO;
import com.lgwork.sys.service.SysUserService;
import com.lgwork.sys.service.UserAccountService;


/**
 * 
 * 账号控制器
 * 
 * @author irays
 *
 */
@RequestMapping("/user/account")
@Controller
public class UserAccountController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/user/account";
	
	/**
	 * 账号服务接口
	 */
	@Autowired
	private UserAccountService userAccountService;
	
	/**
	 * 系统账户
	 */
	@Autowired
	private SysUserService sysUserService; 
	
	
	/**
	 *  账号首页
	 * @return
	 */
	@GetMapping("/index.do")
	public String index(ModelMap map) {
		
		UserAccountPO userAccountPO = new UserAccountPO();
		userAccountPO.setPhone("1111");
		map.addAttribute("aaaa", userAccountPO);
		
		return BASE_PATH + "/index";
	}
	
	
	/**
	 * 
	 *  分页参数的实现
	 * 
	 * @param pageNum
	 * @param pageSize
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<UserAccountPO>> pageListUserAccount(
		 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
		 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
		 UserAccountPO userAccount) {
		
		
		Page<UserAccountPO> pageSearchByCondition = userAccountService.pageSearchByCondition(pageNum, pageSize, userAccount);
		
		
		return new PageResult<UserAccountPO>(pageSearchByCondition);
	}
	
	
	
	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public BaseResult<String> deleteUserAccount(@PathVariable("id") Long id) {
		
		// 根据id删除数据
		BaseResult<String> result =  userAccountService.deleteUserAccount(id);
		
		return  result;
	}
	
	
	/**
	 * 删除数据
	 * @param userAccountPO
	 * @return
	 */
	@PostMapping("/")
	@ResponseBody
	public BaseResult<String> createUserAccount(UserAccountPO userAccountPO) {
		if(userAccountPO == null) {
			return  new BaseResultBuilder<String>().fail("数据不能为空").build();
		}
		
		return userAccountService.saveUserAccount(userAccountPO);
	}
	
	
	/**
	 * 根据id修改用户信息
	 * 
	 * @param userAccountPO
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	@ResponseBody
	public BaseResult<String>  updateUserAccount(@PathVariable(value="id", required=true) Long id, 
			UserAccountPO userAccountFromFront) {
		
		return userAccountService.updateUserAccount(id, userAccountFromFront);
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
		
		// 根据id获取用户
		final UserAccountPO userAccountPO = userAccountService.getById(id);
		if(userAccountPO == null) {
			return "/error/popup404";
		}
		
		Map<String, Object>  entity = new HashMap<String, Object>(4);
		
		// 构建模型
		 entity.put("username", userAccountPO.getUsername());
		 entity.put("email", userAccountPO.getEmail());
		 entity.put("phone", userAccountPO.getPhone());
		 entity.put("id", userAccountPO.getId());
		
		model.addAttribute("model", entity);
		
		return BASE_PATH + "/edit";
	}
	
	
	@GetMapping("/sysedit.do")
	public String syseditAction( ModelMap model, Long id ) {
		
		UserAccountPO userAccountPO = userAccountService.getById(id);
		
		if(userAccountPO == null) {
			return "/error/popup404";
		}
		
		SysUserPO sysUserPO = sysUserService.getByUcode(userAccountPO.getUcode());
		
		// 设置模型
		model.addAttribute("account", userAccountPO);
		model.addAttribute("sysUser", sysUserPO);
		
		return BASE_PATH + "/sysedit";
	}
	
	
	@PostMapping("/sysUser/{ucode}")
	@ResponseBody
	public BaseResult<String> saveSysUser(@PathVariable("ucode") String ucode, SysUserPO sysUserPO) {
		
		return userAccountService.saveSysUser(ucode, sysUserPO);
	}
	
	
	
	
	

}
