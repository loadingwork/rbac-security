package com.iveiv.rbac.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.iveiv.rbac.base.BasePO;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户组表实体
 * @author irays
 *
 */
@Table(name = "sys_group")
@Entity
@Setter
@Getter
public class SysGroupPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8321427245269572685L;
	
	/**
	 * 用户组编码
	 */
	@NotNull
	@Column(name="sys_groupr_code", length=32, unique=true, nullable=false)
	private String sysGrouprCode;
	
	/**
	 * 用户组编码
	 */
	@NotNull
	@Column(name="sys_group_name", length=255, nullable=false)
	private String sysGroupName;


}
