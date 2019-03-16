package com.lgwork.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.PageResult;
import com.lgwork.domain.po.DictCategoryPO;
import com.lgwork.domain.vo.ZtreeNodeVO;
import com.lgwork.sys.service.DictCategoryService;


/**
 * 数据字典分类控制
 * @author irays
 *
 */
@RequestMapping("/dict/category")
@Controller
public class DictCategoryController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/dict/category";
	
	/**
	 * 数据字典分类控制
	 */
	@Autowired
	private DictCategoryService dictCategoryService;
	
	
	/**
	 * 分页搜索实现
	 * @param pageNum
	 * @param pageSize
	 * @param fgetDictCategoryPO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<DictCategoryPO>> searchGetSysRole(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final DictCategoryPO fgetDictCategoryPO
			) {
		
		Page<DictCategoryPO> pageSearchByCondition = dictCategoryService.pageSearchByCondition(pageNum, pageSize, fgetDictCategoryPO);
		
		
		return new PageResult<DictCategoryPO>(pageSearchByCondition);
	}
	
	
	public BaseResult<List<ZtreeNodeVO>> pageTreeByCondition(
			@RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final DictCategoryPO fgetDictCategoryPO
			 ) {
		
		
		return null;
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
		BaseResult<List<ZtreeNodeVO>> result = dictCategoryService.listAllChildrenTnode(pcode);
		
		return result;
	}
	
	
	
	/**
	 * 创建
	 * @param fgetSysRolePO
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/")
	@ResponseBody
	public BaseResult<String> create(DictCategoryPO fgetDictCategoryPO) throws Exception {
		
		// 保存数据
		BaseResult<String> result = dictCategoryService.saveDictCategoryPO(fgetDictCategoryPO); 
		
		return result;
	}
	
	
	/**
	 * 修改
	 * @param id
	 * @param fgetDictCategoryPO
	 */
	@PutMapping("/{id}")
	@ResponseBody
	public BaseResult<String> update(@PathVariable("id") Long id, 
			DictCategoryPO fgetDictCategoryPO) {
		
		BaseResult<String> result =  dictCategoryService.updateDictCategoryPO(id, fgetDictCategoryPO);
		
		return result;
		
	}
	
	
	/**
	 * 删除
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public BaseResult<String> delete(@PathVariable("id") Long id) {
		
		BaseResult<String> result =  dictCategoryService.deleteById(id);
		
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
	 * 插件是否是第一次加载
	 * @return
	 */
	@GetMapping("/checkFirstAdd")
	@ResponseBody
	public BaseResult<String>  checkFirstAdd() {
		
		BaseResult<String> result = dictCategoryService.handleCheckFirstAdd();
		
		return result;
	}
	
	
	/***
	 * 根据code获取数据
	 * @param code
	 * @return
	 */
	@GetMapping("/code/{code}")
	@ResponseBody
	public BaseResult<DictCategoryPO>  getDictCategoryByCode(@PathVariable("code") String code) {
		
		// 根据code获取数据
		BaseResult<DictCategoryPO> result = dictCategoryService.getDictCategoryByCode(code);
		
		return result;
	} 
	
	
	/**
	 * 根据code删除数据
	 * @param code
	 * @return
	 */
	@DeleteMapping("/code/{code}")
	@ResponseBody
	public BaseResult<String>  deleteByCode(@PathVariable("code") String code) {
		
		BaseResult<String> result = dictCategoryService.deleteByCode(code);
		
		return result;
	}
	
	/**
	 * 根据code修改
	 * @param code
	 * @param fgetDictCategoryPO
	 * @return
	 */
	@PutMapping("/code/{code}")
	@ResponseBody
	public BaseResult<String>  updateByCode(@PathVariable("code") String code, DictCategoryPO fgetDictCategoryPO) {
		
		BaseResult<String> result = dictCategoryService.updateByCode(code, fgetDictCategoryPO);
		
		return result;
	}
	
	
	

}
