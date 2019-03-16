package com.lgwork.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lgwork.base.BasePO;
import com.lgwork.base.jpa.convert.BoolConvert;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 文件储存
 * 
 * @author irays
 *
 */
@Table(name = "file_storage")
@Entity
@Setter
@Getter
public class FileStoragePO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4663303822427911625L;
	/**
	 * 编码
	 */
	@Column(name = "file_code", length = 64)
	private String fileCode;
	/**
	 * 文件key 
	 * 实际为文件本地访问相对路径
	 */
	@Column(name = "file_key", length = 500)
	private String fileKey;
	/**
	 * 描述
	 */
	@Column(name = "description", length = 255)
	private String description;
	/**
	 * 原始文件名
	 */
	@Column(name = "original_file_name", length = 255)
	private String originalFilename;
	/**
	 * 后缀名, 请使用全小写
	 */
	@Column(name = "ext", length = 32)
	private String ext;
	/**
	 * 文件大小
	 * 
	 * 8 bit = 1 Byte ；1024 B = 1 KB （KiloByte） ；1024 KB = 1 MB （MegaByte） ；1024 MB = 1 GB （GigaByte） ；1024 GB = 1 TB （TeraByte）
	 * 
	 */
	@Column(name = "file_size", length = 32)
	private Long fileSize;
	/**
	 * 文件单位
	 * B
	 */
	@Column(name = "size_unit", length = 32)
	private String sizeUnit;
	/**
	 * 储存的名称
	 */
	@Column(name = "storage_name", length = 255)
	private String storageName;

	/**
	 *  文件过期时间
	 *  
	 *  enabled 为 1, 参数失效
	 *  
	 */
	@Column(name = "temp_expires_at", length = 32)
	private Date tempExpiresAt;

	/**
	 * 储存文件业务类型
	 * default 
	 */
	@Column(name = "storage_type", length = 12)
	private String storageType;
	/**
	 * 创建人
	 */
	@Column(name = "create_by", length = 32)
	private String createBy;
	/**
	 * 是否可用, 一般用户文件先上传后处理
	 */
	@Convert(converter=BoolConvert.class)
	@Column(name = "is_enabled", length = 5, nullable=false)
	private Boolean enabled;

	

}
