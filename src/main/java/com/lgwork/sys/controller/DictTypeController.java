package com.lgwork.sys.controller;

import java.util.List;
import java.util.Optional;

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
import com.lgwork.domain.po.DictTypePO;
import com.lgwork.sys.service.DictTypeService;

/**
 * 数据字典控制器
 * @author irays
 *
 */
@RequestMapping("/dict/type")
@Controller
public class DictTypeController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/dict/dict";
	
	/**
	 * 数据字典服务接口
	 */
	@Autowired
	private DictTypeService dictTypeService;
	
	/**
	 * 分页搜索实现
	 * @param pageNum
	 * @param pageSize
	 * @param fgetDictTypePO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<DictTypePO>> searchGetSysRole(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final DictTypePO fgetDictTypePO
			) {
		
		Page<DictTypePO> pageSearchByCondition = dictTypeService.pageSearchByCondition(pageNum, pageSize, fgetDictTypePO);
		
		
		return new PageResult<DictTypePO>(pageSearchByCondition);
	}
	
	
	/**
	 * 创建
	 * @param fgetDictTypePO
	 * @param dictCategoryId
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/")
	@ResponseBody
	public BaseResult<String> create(DictTypePO fgetDictTypePO,  String dictCategoryCode) throws Exception {
		
		// 处理保存数据
		BaseResult<String> result = dictTypeService.saveDictTypePO(fgetDictTypePO, dictCategoryCode);
		
		return result;
	}
	
	
	
	/**
	 * 修改
	 * @param id
	 * @param fgetDictTypePO
	 * @return
	 */
	@PutMapping("/")
	@ResponseBody
	public BaseResult<String> update(DictTypePO fgetDictTypePO) {
		
		// 修改
		BaseResult<String> result =  dictTypeService.updateDictTypePO(fgetDictTypePO);
		
		return result;
		
	}
	
	
	
	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@ResponseBody
	public BaseResult<String> delete(@PathVariable("id") Long id) {
		
		BaseResult<String> result =  dictTypeService.deleteById(id);
		
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
	 * 打开增加页面
	 * @return
	 */
	@GetMapping("/add.do")
	public String add() {
		return BASE_PATH + "/add";
	}
	
	
	/**
	 * 打开编辑页面
	 * @param modelMap
	 * @param id
	 * @return
	 */
	@GetMapping("/edit.do")
	public String edit(ModelMap modelMap,Long id) {
		
//		DictItemPO entity = dictItemService.getById(id);
		
		Optional<DictTypePO> dbDictTypePO = dictTypeService.getById(id);
		
		if (!dbDictTypePO.isPresent()) {
			// 去404
			return ErrorPageConst.ERROR404;
		}
		
		modelMap.put("entity", dbDictTypePO.get());
		
		return BASE_PATH + "/edit"; 
	}
	
	

}
