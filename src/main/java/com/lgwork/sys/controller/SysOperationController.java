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
import com.lgwork.domain.po.SysOperationPO;
import com.lgwork.sys.service.SysOperationService;

/**
 * 操作控制器
 * @author irays
 *
 */
@RequestMapping("/sys/operation")
@Controller
public class SysOperationController {
	
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/permission/operation";
	
	/**
	 * 操作服务接口
	 */
	@Autowired
	private SysOperationService  sysOperationService;
	
	/**
	 * 分页搜索实现
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysRolePO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<SysOperationPO>> searchGetSysRole(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final SysOperationPO fgetSysOperationPO
			) {
		
		// 分业获取操作
		Page<SysOperationPO> result = sysOperationService.pageSearchByCondition(pageNum, pageSize, fgetSysOperationPO);
		
		
		return new PageResult<SysOperationPO>(result);
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
		
		// 根据id获取参数
		SysOperationPO sysOperationPO = sysOperationService.getById(id);
		
		if(sysOperationPO == null) {
			return ErrorPageConst.ERROR404;
		}
		
		model.addAttribute("model", sysOperationPO);
		
		return BASE_PATH + "/edit";
	}
	
	
	
	/**
	 * 创建
	 * @param fgetSysRolePO
	 * @return
	 */
	@PostMapping("/")
	@ResponseBody
	public BaseResult<String> create(SysOperationPO fgetSysOperationPO) {
		
		// 保存操作
		BaseResult<String> result = sysOperationService.saveSysOperationPO(fgetSysOperationPO);
		
		return result;
	}
	
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param fgetSysRolePO
	 * @return
	 */
	@PutMapping("/{id}")
	@ResponseBody
	public BaseResult<String> update(@PathVariable("id") Long id, 
			SysOperationPO fgetSysOperationPO) {
		
		BaseResult<String> result =  sysOperationService.updateSysOperationPO(id, fgetSysOperationPO);
		
		return result;
	}
	
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public BaseResult<String> delete(@PathVariable("id") Long id) {
		
		BaseResult<String> result =  sysOperationService.deleteById(id);
		
		return result;
	}
	
	

}
