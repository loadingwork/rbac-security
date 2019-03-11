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
 * 系统用户与角色关联实体
 * 
 * @author irays
 *
 */
@Table(name = "sys_user_role")
@Entity
@Setter
@Getter
public class SysUserRolePO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5622907037783234837L;

	@NotNull
	@Column(name = "sys_user_id", length = 32, nullable=false)
	private Long sysUserId;

	@NotNull
	@Column(name = "sys_role_id", length = 32, nullable=false)
	private Long sysRoleId;


}
