package com.iveiv.rbac.sys.service;


/**
 * 数据字典工具服务接口
 * 注: 数据字典的查询, 数据字典的转换
 * @author irays
 *
 */
public interface DictService {
	
	
	
	/**
	 * 数据字典转换
	 * @param typeCode   数据字典类型编码
	 * @param dictCode   数据字典项编码
	 * @return  数据字典名称
	 */
	String dictConvertToText(String typeCode, String dictCode);
	
	/**
	 * 数据字典配置项id数据字典转换
	 * @param id  数据字典配置项id
	 * @return  数据字典名称
	 */
	String dictIdConvertToText(Long  id);
	
	

}
