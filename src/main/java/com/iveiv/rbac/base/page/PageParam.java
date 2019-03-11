package com.iveiv.rbac.base.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * 分页参数传递工具类
 * 
 * @author irays
 *
 */
@Setter
@Getter
public class PageParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8998460334367140897L;

	/**
	 * 当前页数
	 */
	private int pageNum;

	/**
	 * 每页记录数
	 */
	private int pageSize;

	

}
