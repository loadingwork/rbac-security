package com.iveiv.rbac.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.iveiv.rbac.base.BasePO;

import lombok.Getter;
import lombok.Setter;


/**
 * 菜单与权限关联表实体
 * @author irays
 *
 */
@Table(name="sys_menu_permission")
@Entity
@Setter
@Getter
public class SysMenuPermissionPO extends BasePO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3850156639286124924L;


	/**
	 * 权限表id
	 */
	@NotNull
	@Column(name="sys_permission_id", length=32, nullable=false)
	private Long sysPermissionId;
	
	
	@NotNull
	@Column(name="sys_operation_id", length=32, nullable=false)
	private Long sysOperationId;

	

}
