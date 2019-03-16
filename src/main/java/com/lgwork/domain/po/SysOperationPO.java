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
 * 权限操作表实体
 * 
 * @author irays
 *
 */
@Table(name = "sys_operation")
@Entity
@Setter
@Getter
public class SysOperationPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2497265203594946091L;

	/**
	 * 操作名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 权限编码
	 */
	@Column(name = "operation_code", length = 255)
	private String operationCode;
	
	/**
	 * 匹配类型
	 * ant 
	 * pattern
	 */
	@Column(name = "match_type", length = 32)
	private String matchType;
	
	/**
	 * 匹配表达式
	 */
	@Column(name = "match_pattern", length = 255)
	private String matchPattern;

	/**
	 * 备注
	 */
	@Column(name = "remarks", length = 255)
	private String remarks;
	
	/**
	 * 是否启用
	 */
	@Convert(converter=BoolConvert.class)
	@NotNull
	@Column(name="is_enabled", length=5, nullable=false)
	private Boolean enabled;

	

}
