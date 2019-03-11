package com.iveiv.rbac.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.iveiv.rbac.base.BasePO;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户与用户组关联表实体
 * 
 * @author irays
 *
 */
@Table(name = "sys_group_user")
@Entity
@Setter
@Getter
public class SysGroupUserPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 218628064655471737L;

	@NotNull
	@Column(name = "sys_user_code", length = 32, nullable=false)
	private String sysUserCode;

	@NotNull
	@Column(name = "sys_groupr_code", length = 32, nullable=false)
	private String sysGrouprCode;

	

}
