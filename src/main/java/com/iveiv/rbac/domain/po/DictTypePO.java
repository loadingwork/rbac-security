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
 * 字典类型表
 * 
 * @author irays
 *
 */
@Table(name = "dict_type")
@Entity
@Setter
@Getter
public class DictTypePO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 774192188828481121L;

	/**
	 *  数据字典分类编码
	 */
	@NotNull
	@Column(name = "dict_category_code", nullable=false)
	private String dictCategoryCode;
	/**
	 * 数据字典编码
	 */
	@Column(name = "type_code", length = 32, unique = true, nullable=false)
	private String typeCode;
	/**
	 * 数据字典名称
	 */
	@Column(name = "text", length = 32)
	private String text;
	/**
	 * 数据字典状态
	 */
	@Column(name = "status", length = 12)
	private String status;
	/**
	 * 数据库对应的列, 主要作用为备注
	 * 
	 * 例如:  aaa_bbb, bbb_aa
	 * 
	 */
	@Column(name = "table_col", length = 500)
	private String tableCol;

	

}
