package com.lgwork.domain.po;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lgwork.base.BaseTreePO;
import com.lgwork.base.jpa.convert.BoolConvert;
import com.lgwork.base.jpa.convert.SysMenuTypeEnumConvert;
import com.lgwork.enums.SysMenuTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 系统菜单表实体
 * 
 * @author irays
 *
 */
@Table(name = "sys_menu")
@Entity
@Setter
@Getter
public class SysMenuPO extends BaseTreePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7850826582235178290L;
	/**
	 * 菜单名称简写
	 */
	@Column(name = "name", length = 255, nullable=false)
	private String name;
	/**
	 * 访问的uri
	 */
	@Column(name = "request_uri", length = 255, nullable=false)
	private String requestUri;
	/**
	 * 访问方式
	 */
	@Column(name = "method", length = 32, nullable=false)
	private String method;
	/**
	 * icon地址
	 */
	@Column(name = "icon_src", length = 64)
	private String iconSrc;
	/**
	 * 菜单类型
	 * menu 菜单 
	 * btn 按钮
	 * 
	 */
	@Convert(converter=SysMenuTypeEnumConvert.class)
	@Column(name = "menu_type", length = 64, nullable=false)
	private SysMenuTypeEnum menuType;
	/**
	 * 排序
	 */
	@Column(name = "menu_sort", length = 11, nullable=false)
	private Integer menuSort;
	/**
	 * 是否可用
	 */
	@Convert(converter=BoolConvert.class)
	@Column(name = "is_enabled", length = 5, nullable=false)
	private Boolean enabled;
	/**
	 * 菜单是否打开
	 */
	@Convert(converter=BoolConvert.class)
	@Column(name = "is_open", length = 5, nullable=false)
	private Boolean open;
	/**
	 * 备注
	 */
	@Column(name = "remarks", length = 500)
	private String remarks;

	

}
