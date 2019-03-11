package com.iveiv.rbac.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 日记类型
 * 
 * @author irays
 *
 */
public enum SysLogEnum {

	/**
	 *  仅增加
	 */
	CREATE("create"), 
	/**
	 * 新增 与 修改
	 */
	SAVE("save"), 
	/**
	 * 仅修改
	 */
	MODIFY("modify"),
	/**
	 * 修改
	 */
	UPDATE("update"),
	/**
	 * 物理删除
	 */
	DELETE("delete"), 
	/**
	 * 逻辑删除
	 */
	REMOVE("remove"),
	/**
	 * 获取单个对象
	 */
	GET("get"), 
	/**
	 * 获取多个对象
	 */
	LIST("list"),
	/**
	 * 搜索多个对象
	 */
	 SEARCH("search"),
	 /**
	  * 处理
	  */
	 HANDLE("handle"),
	/**
	 * 其他
	 */
	OTHER("other");

	private String code;

	SysLogEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String value() {
		return code;
	}

	public static SysLogEnum getEnum(String code) {
		SysLogEnum resultEnum = null;
		SysLogEnum[] enumAry = SysLogEnum.values();

		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		SysLogEnum[] ary = SysLogEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {

			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key, code);

		}
		return enumMap;
	}

}
