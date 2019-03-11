package com.iveiv.rbac.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.iveiv.rbac.base.BasePO;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 用户组与角色关联表实体
 * 
 * @author irays
 *
 */
@Table(name = "sys_group_role")
@Entity
@Setter
@Getter
public class SysGroupRolePO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3281305414859767062L;

	/**
	 * 角色id
	 */
	@NotNull
	@Column(name = "sys_role_id", nullable=false)
	private Long sysRoleId;

	/**
	 * 角色编码
	 */
	@NotNull
	@Column(name = "sys_groupr_code", length = 32, nullable=false)
	private String sysGrouprCode;

	

}
