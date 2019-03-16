package com.lgwork.domain.po;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lgwork.base.BaseTreePO;
import com.lgwork.base.jpa.convert.BoolConvert;

import lombok.Getter;
import lombok.Setter;


/**
 * 数字字典分类
 * @author irays
 *
 */
@Table(name = "dict_category")
@Entity
@Setter
@Getter
public class DictCategoryPO extends BaseTreePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2890908229962914085L;

	/**
	 * 字典分类名称
	 */
	@NotNull
	@Column(name = "category_name", length = 100, nullable=false)
	private String categoryName;
	
	/**
	 * 是否可用
	 */
	@Convert(converter=BoolConvert.class)
	@Column(name = "is_enabled", length = 5, nullable=false)
	private Boolean enabled;
	
	
	
	

}
