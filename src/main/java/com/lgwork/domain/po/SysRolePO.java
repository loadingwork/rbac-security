package com.lgwork.domain.po;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lgwork.base.BasePO;
import com.lgwork.base.jpa.convert.BoolConvert;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 角色实体
 * 
 * @author irays
 *
 */
@Table(name = "sys_role")
@Entity
@Setter
@Getter
public class SysRolePO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4807841829039522765L;

	/**
	 * 角色编码
	 */
	@NotNull
	@Column(name = "role_code", length = 64, unique = true, nullable=false)
	private String roleCode;

	/**
	 * 名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 描述
	 */
	@Column(name = "describtion")
	private String describtion;

	/**
	 * 备注
	 */
	@Column(name = "remarks")
	private String remarks;

	/**
	 * 是否可用
	 */
	@Convert(converter=BoolConvert.class)
	@NotNull
	@Column(name="is_enabled", length=5, nullable=false)
	private Boolean enabled;

}
