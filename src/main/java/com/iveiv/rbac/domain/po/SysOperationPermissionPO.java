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
 * 操作与权限关联表实体
 * 
 * @author irays
 *
 */
@Table(name = "sys_operation_permission")
@Entity
@Setter
@Getter
public class SysOperationPermissionPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 814351430720462015L;

	/**
	 * 权限表id
	 */
	@NotNull
	@Column(name = "sys_permission_id", length = 32, nullable=false)
	private Long sysPermissionId;

	/**
	 * 菜单表id
	 */
	@NotNull
	@Column(name = "sys_menu_id", length = 32, nullable=false)
	private Long sysMenuId;

	

}
