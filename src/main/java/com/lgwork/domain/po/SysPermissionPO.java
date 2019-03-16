package com.lgwork.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lgwork.base.BasePO;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 权限表实体
 * 
 * @author irays
 *
 */
@Table(name = "sys_permission")
@Entity
@Setter
@Getter
public class SysPermissionPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 959906154383385541L;

	/**
	 * 权限编码
	 */
	@NotNull
	@Column(name = "sys_permission_code", length = 64, unique = true, nullable=false)
	private String sysPermissionCode;

	/**
	 * 
	 * 权限描述
	 * 
	 */
	@NotNull
	@Column(name = "permission_desc", length = 255, nullable=false)
	private String permissionDesc;

	/**
	 * 
	 * 权限名称
	 * 
	 */
	@Column(name = "name", length = 255)
	private String name;

	/**
	 * 
	 * 权限备注
	 * 
	 */
	@Column(name = "remarks", length = 255)
	private String remarks;

	

}
