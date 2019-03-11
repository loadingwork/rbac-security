package com.iveiv.rbac.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页控制器
 * 快捷跳转
 * @author irays
 *
 */
@Controller
public class IndexController {
	
	
	@RequestMapping({"", "/"})
	public String index() {
		return "redirect:/home/index.do";
	}
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("/sys_login")
	public String loginPage() {
		return "redirect:/login/login.do";
	}
	

}
