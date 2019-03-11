package com.iveiv.rbac.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * ztree数据表
 * 
 * @author irays
 *
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseTreePO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1794225164985383440L;
	
	/**
	 * top 父节点
	 */
	public static final String TOP_PROOT = "PROOT";
	/**
	 * top 节点
	 */
	public static final String TOP_ROOT = "root";
	
	/**
	 * 父级编码
	 * 
	 * 最高级父级默认:  PROOT 
	 * 
	 */
	@Column(name = "pcode", length = 16, nullable = false)
	private String pcode;
	
	/**
	 * 分类编码
	 * 
	 * 最高级别默认: root
	 * 
	 */
	@Column(name="code", length = 16, unique=true, nullable = false)
	private String code;
	
	/**
	 * 所有的父级编码
	 * 例如:  aaa,bbb,ccc
	 */
	@Column(name = "pcodes", length=256, nullable = false)
	private String pcodes;
	
	

}
