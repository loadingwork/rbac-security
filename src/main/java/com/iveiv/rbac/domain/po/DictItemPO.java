package com.iveiv.rbac.domain.po;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.iveiv.rbac.base.BasePO;
import com.iveiv.rbac.base.jpa.convert.BoolConvert;

import lombok.Getter;
import lombok.Setter;

/**
 * 无数种叫法
 * 
 * 数据字典值表
 * 数据字典项表
 * 字典明细表
 * 字典类型条目表
 * 
 * @author irays
 *
 */
@Table(name = "dict_item")
@Entity
@Setter
@Getter
public class DictItemPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5454414318753440039L;

	/**
	 * 主表编码
	 */
	@NotNull
	@Column(name = "type_code", length = 32, nullable=false)
	private String typeCode;

	/**
	 * 业务代码
	 */
	@NotNull
	@Column(name = "dict_code", length = 32, unique=true, nullable=false)
	private String dictCode;
	
	/**
	 * 排序
	 */
	@NotNull
	@Column(name = "sort", nullable=false)
	private Integer sort;

	/**
	 * 配置项名称
	 */
	@Column(name = "text", length = 32)
	private String text;
	/**
	 * 备注
	 */
	@Column(name = "remark", length = 255)
	private String remark;

	/**
	 *  是否使用
	 *  逻辑删除处理
	 */
	@NotNull
	@Convert(converter=BoolConvert.class)
	@Column(name = "is_enabled", length = 5, nullable=false)
	private Boolean enabled;

	

}
