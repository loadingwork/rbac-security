package com.lgwork.base.ext.dict;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义数据字典类型
 * 注:   仅仅适用于与前端关联很重的页面
 * 例如: VO层对象
 * @author irays
 *
 */
@Getter
@Setter
public class DictStruct {
	
	
	/**
	 * 数据字典编码
	 * @return
	 */
	String type;
	
	/**
	 * 数据字典项名称
	 * @return
	 */
	String text;
	
	/**
	 * 数据字典项编码
	 * @return
	 */
	String code;
	

}
