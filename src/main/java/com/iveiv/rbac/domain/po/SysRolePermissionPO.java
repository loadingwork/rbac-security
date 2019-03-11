package com.iveiv.rbac.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.iveiv.rbac.base.BasePO;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色与权限关联实体
 * 
 * @author irays
 *
 */
@Table(name="sys_role_permission")
@Entity
@Setter
@Getter
public class SysRolePermissionPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1202352117651378011L;

	/**
	 * 标识类型
	 */
	@NotNull
	@Column(name = "principal_type", length=12, nullable=false)
	private String principalType;

	/**
	 * 标识id
	 */
	@NotNull
	@Column(name = "principal_id", length=32, nullable=false)
	private Long principalId;

	/**
	 * 权限id
	 */
	@NotNull
	@Column(name = "sys_permission_id", length=32, nullable=false)
	private Long sysPermissionId;


}
