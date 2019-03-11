package com.iveiv.rbac.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 随机数业务类型
 * @author irays
 *
 */
public enum SysRandomStrTypeEnum {
	
	
	/**
	 * 随机数  
	 */
	RANDOM("r", 10),
	/**
	 * 数字
	 */
	NUM("num", 8),
	/**
	 * 菜单
	 */
	MENU("menu", 8),
	/**
	 * 数据字典分类
	 */
	DICT_C("dict_c", 6),
	/**
	 * 数据字典
	 */
	DICT_T("dict_t", 6),
	/**
	 * 数据字典值
	 */
	DICT_I("dict_i", 6);
	

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private SysRandomStrTypeEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}
	
	/**
	 * 获取名称
	 * @return
	 */
	public String desc() {
		return desc;
	}
	
	/**
	 * 获取value
	 * @return
	 */
	public int value() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public static SysRandomStrTypeEnum getEnum(int value) {
		SysRandomStrTypeEnum resultEnum = null;
		SysRandomStrTypeEnum[] enumAry = SysRandomStrTypeEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		SysRandomStrTypeEnum[] ary = SysRandomStrTypeEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}
	
}
