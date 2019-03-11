package com.iveiv.rbac.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.base.result.PageResult;
import com.iveiv.rbac.domain.po.SysLoginLogPO;
import com.iveiv.rbac.sys.service.SysLoginLogService;

/**
 * 系统登录log
 * @author irays
 *
 */
@RequestMapping("/sys/loginlog")
@Controller
public class SysLoginLogController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/log/loginlog";
	
	
	/**
	 * 系统登录日记服务接口
	 */
	@Autowired
	private SysLoginLogService sysLoginLogService;
	
	
	/**
	 *  列表搜索展示接口
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysLoginLogPO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<SysLoginLogPO>> search(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final SysLoginLogPO fgetSysLoginLogPO
			) {
		
		Page<SysLoginLogPO> pageSearchByCondition = sysLoginLogService.pageSearchByCondition(pageNum, pageSize, fgetSysLoginLogPO);
		
		
		return new PageResult<SysLoginLogPO>(pageSearchByCondition);
	}
	
	
	/**
	 * 首页
	 * @return
	 */
	@GetMapping("/index.do")
	public String index() {
		return BASE_PATH + "/index";
	}
	
	

}
