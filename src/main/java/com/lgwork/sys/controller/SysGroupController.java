package com.lgwork.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 用户分组
 * @author irays
 *
 */
@RequestMapping("/sys/group")
@Controller
public class SysGroupController {
	
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "";
	
	/**
	 * 分组主页
	 * @return
	 */
	@GetMapping("/index.do")
	public String index() {
		return BASE_PATH + "/index";
	}
	
	
	

}
