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
import com.iveiv.rbac.domain.po.SysErrorLogPO;
import com.iveiv.rbac.sys.service.SysErrorLogService;


/**
 * 系统错误日记控制器
 * @author irays
 *
 */
@RequestMapping("/sys/errorlog")
@Controller
public class SysErrorLogController {
	
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/log/errorlog";
	
	
	/**
	 * 系统错误日记服务接口
	 */
	@Autowired
	private SysErrorLogService sysErrorLogService;
	
	
	/**
	 *  列表搜索展示接口
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysErrorLogPO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<SysErrorLogPO>> search(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final SysErrorLogPO fgetSysErrorLogPO
			) {
		
		Page<SysErrorLogPO> pageSearchByCondition = sysErrorLogService.pageSearchByCondition(pageNum, pageSize, fgetSysErrorLogPO);
		
		
		return new PageResult<SysErrorLogPO>(pageSearchByCondition);
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
