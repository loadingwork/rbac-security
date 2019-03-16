package com.lgwork.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 审计实体类
 * 
 * @author irays
 *
 */
@ApiModel("审计对象")
@Entity
@Table(name = "auditing_entity")
@EntityListeners(AuditingEntityListener.class)
public class AuditingEntityPO  {
	
	/**
	 *   主键自增
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建日期")
	@CreatedDate
	@Column(name="gmt_create", nullable=false)
	private Date gmtCreate;
	
	/**
	 * 创建者
	 */
	@ApiModelProperty("创建者")
    @CreatedBy
    @Column(name="created_by")
    private String createdBy;
	
	/**
	 * 最后修改时间
	 */
	@ApiModelProperty("最后修改时间")
    @LastModifiedDate
    @Column(name="last_modified_at")
    private Date lastModifiedAt;

	/**
	 * 最后修改者
	 */
    @ApiModelProperty("最后修改者")
    @LastModifiedBy
    @Column(name="last_modified_by")
    private String lastModifiedBy;
    /**
     * 版本号
     */
    @ApiModelProperty("版本号")
    @Version
    private Long version;

    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
	
	
	
	
	

}
