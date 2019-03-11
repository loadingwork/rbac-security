package com.iveiv.rbac.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * title: 全局是否枚举 <br/>
 * description: TODO <br/>
 *
 * @author irays
 * @date 2018/05/28
 */
public enum BoolEnum {

	/**
	 * 是
	 */
	TRUE("true", 1),
	/**
	 * 否
	 */
	FALSE("true", 0);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private BoolEnum(String desc, int value) {
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
	
	public int value() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public static BoolEnum getEnum(int value) {
		BoolEnum resultEnum = null;
		BoolEnum[] enumAry = BoolEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		BoolEnum[] ary = BoolEnum.values();
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
