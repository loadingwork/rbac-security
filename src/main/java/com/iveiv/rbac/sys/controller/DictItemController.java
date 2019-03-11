package com.iveiv.rbac.sys.controller;

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

import com.iveiv.rbac.base.constant.ErrorPageConst;
import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.base.result.PageResult;
import com.iveiv.rbac.domain.po.DictItemPO;
import com.iveiv.rbac.sys.service.DictItemService;

/**
 * 数据字典值控制器
 * @author irays
 *
 */
@RequestMapping("/dict/item")
@Controller
public class DictItemController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/dict/item";
	
	/**
	 * 数据字典值服务接口
	 */
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 分页搜索实现
	 * @param pageNum
	 * @param pageSize
	 * @param fgetDictItemPO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<DictItemPO>> searchGetSysRole(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final DictItemPO fgetDictItemPO
			) {
		
		Page<DictItemPO> pageSearchByCondition = dictItemService.pageSearchByCondition(pageNum, pageSize, fgetDictItemPO);
		
		
		return new PageResult<DictItemPO>(pageSearchByCondition);
	}
	
	
	/**
	 * 通过typeCode获取对象
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/listDictItemPoByTypeCode")
	@ResponseBody
	public BaseResult<List<DictItemPO>> listDictItemPoByTypeCode(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize,
			 String typeCode
			) {
		
		// 分页获取
		Page<DictItemPO> result = dictItemService.listDictItemPoByTypeCode(pageNum, pageSize, typeCode);
		
		
		return new PageResult<DictItemPO>(result);
	}
	
	
	/**
	 * 创建
	 * @param fgetDictItemPO
	 * @param dictTypeCode
	 * @return
	 */
	@PostMapping("/")
	@ResponseBody
	public BaseResult<String> create(DictItemPO fgetDictItemPO,  String typeCode) {
		
		// 处理保存数据
		BaseResult<String> result = dictItemService.saveDictItemPO(fgetDictItemPO, typeCode);
		
		return result;
	}
	
	
	
	
	/**
	 * 修改
	 * @param id
	 * @param fgetDictItemPO
	 * @return
	 */
	@PutMapping("/{id}")
	@ResponseBody
	public BaseResult<String> update(@PathVariable("id") Long id, 
			DictItemPO fgetDictItemPO) {
		
		BaseResult<String> result =  dictItemService.updateDictItemPO(id, fgetDictItemPO);
		
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
		
		BaseResult<String> result =  dictItemService.deleteById(id);
		
		return result;
	}
	
	/**
	 * 增加操作
	 * @param modelMap
	 * @param typeCode 值可传, 可不传
	 * @return
	 */
	@GetMapping("/add.do")
	public String add(ModelMap modelMap,String typeCode) {
		
		modelMap.addAttribute("typeCode", typeCode);
		
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
		
		DictItemPO entity = dictItemService.getById(id);
		
		if (entity == null) {
			// 去404
			return ErrorPageConst.ERROR404;
		}
		
		modelMap.put("entity", entity);
		
		return BASE_PATH + "/edit"; 
	}
	
	

}
