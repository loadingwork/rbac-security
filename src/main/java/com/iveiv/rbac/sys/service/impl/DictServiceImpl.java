package com.iveiv.rbac.sys.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iveiv.rbac.domain.po.DictItemPO;
import com.iveiv.rbac.sys.service.DictItemService;
import com.iveiv.rbac.sys.service.DictService;

import lombok.extern.slf4j.Slf4j;


/**
 * 实现
 * @author irays
 *
 */
@Slf4j
@Service("dictServiceImpl")
public class DictServiceImpl implements DictService {
	
	
	/**
	 * 数据字典配置项服务接口
	 */
	@Autowired
	private DictItemService dictItemService;

	@Override
	public String dictConvertToText(String typeCode, String dictCode) {
		
		if (StringUtils.isAnyEmpty(typeCode, dictCode)) {
			log.debug("数据字典类型: {}, 数据字典项: {}, 参数为空", typeCode, dictCode);
			return "";
		}
		
		// 根据编码获取
		DictItemPO dbDictItemPO = dictItemService.getByTypeCodeAndDictCode(typeCode, dictCode);
		
		if (dbDictItemPO == null) {
			log.debug("数据字典类型: {}, 数据字典项: {}, 查询结果为空", typeCode, dictCode);
			return "";
		}
		
		// 获取名称
		String name = dbDictItemPO.getText();
		
		return name;
	}

	@Override
	public String dictIdConvertToText(Long id) {
		
		if (id == null) {
			log.debug("数据字典项id: {}, 参数为空", id);
			return "";
		}
		
		// 根据id获取
		DictItemPO dbDictItemPO = dictItemService.getById(id);
		
		if (dbDictItemPO == null) {
			log.debug("数据字典项id: {}, 结果为空", id);
			return "";
		}
		
		// 获取名称
		String text = dbDictItemPO.getText();
		
		return text;
	}
	
	
	
	
	
	

}
