package com.lgwork.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 *  主页控制器
 * 
 * @author irays
 *
 */
@RequestMapping("/home")
@Controller
public class HomeController {
	
	/**
	 * 
	 * 主页
	 * 
	 * @return
	 */
	@GetMapping("/index.do")
	public String homeAction() {
		return   "/home/index";
	}
	
	
	/**
	 * http://localhost:8001/swagger-ui.html
	 * @return
	 */
	@GetMapping("/swagger.do")
	public String swaggerPage() {
		return "/home/service/swagger_api";
	}
	
	
	/**
	 * 获取数据库连接池状态
	 * @return
	 */
	@GetMapping("/druid.do")
	public String druidStatus() {
		return "/home/service/druid_status";
	}
	
	

}
