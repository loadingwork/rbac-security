package com.lgwork.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.PageResult;
import com.lgwork.domain.po.SysMenuPO;
import com.lgwork.domain.vo.ZtreeNodeVO;
import com.lgwork.sys.service.SysMenuService;

/**
 * 菜单控制器
 * @author irays
 *
 */
@RequestMapping("/sys/menu")
@Controller
public class SysMenuController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/permission/menu";
	
	/**
	 * 菜单服务接口
	 */
	@Autowired
	private SysMenuService sysMenuService;
	
	/**
	 * 分页搜索实现
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysRolePO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<SysMenuPO>> search(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final SysMenuPO fgetSysMenuPO
			) {
		
		Page<SysMenuPO> pageSearchByCondition = sysMenuService.pageSearchByCondition(pageNum, pageSize, fgetSysMenuPO);
		
		
		return new PageResult<SysMenuPO>(pageSearchByCondition);
	}
	
	
	
	/**
	 * 创建菜单
	 * @param fgetSysMenuPO
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/")
	@ResponseBody
	public BaseResult<String> create(SysMenuPO fgetSysMenuPO) throws Exception {
		
		// 保存数据
		BaseResult<String> result = sysMenuService.saveSysMenuPO(fgetSysMenuPO); 
		
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
		
		// 删除数据
		BaseResult<String> result =  sysMenuService.deleteById(id);
		
		return result;
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
	public String add() {
		return BASE_PATH + "/add";
	}
	
	
	/**
	 * 获取子节点
	 * @param pcode
	 * @return
	 */
	@GetMapping("/tnodes")
	@ResponseBody
	public BaseResult<List<ZtreeNodeVO>> listChildrenTnode(@RequestParam(defaultValue="PROOT") String pcode) {
		
		// 获取子节点
		BaseResult<List<ZtreeNodeVO>> result = sysMenuService.listAllChildrenTnode(pcode);
		
		return result;
	}
	
	/**
	 * 插件是否是第一次加载
	 * @return
	 */
	@GetMapping("/checkFirstAdd")
	@ResponseBody
	public BaseResult<String>  checkFirstAdd() {
		
		// 执行操作
		BaseResult<String> result = sysMenuService.handleCheckFirstAdd();
		
		return result;
	}
	
	
	
	

}
