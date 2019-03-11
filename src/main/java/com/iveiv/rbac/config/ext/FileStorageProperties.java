package com.iveiv.rbac.config.ext;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.iveiv.rbac.util.UuidUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件储存
 * 
 * @author irays
 *
 */
@Slf4j
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "file.storage")
public class FileStorageProperties {

	/**
	 * 文件绝对路径
	 */
	private String fileRoot;
	/**
	 * 文件url
	 */
	private String fileUrl;
	/**
	 * 二维码路径
	 */
	private String qrcodeRoot;
	/**
	 * 二维码url
	 */
	private String qrcodeUrl;
	/**
	 * 预览图访问地址
	 */
	private String previewUrl;
	/**
	 * 储存二级位置
	 */
	private Map<String, String>  secondPositions;
	
	/**
	 * 获取二维码储存key
	 * @return
	 */
	public String localQrcodeRelativeFileKey(String uuid) {
		DateTime dt = new DateTime();
		// 获取年份
		int year = dt.getYear();
		int monthOfYear = dt.getMonthOfYear();
		
		// 构建文件key
		StringBuffer key = new StringBuffer();
		key.append("/");
		key.append(year);
		key.append("/");
		key.append(monthOfYear);
		key.append("/");
		key.append(uuid);
		key.append(".png");
		
		return key.toString();
	}
	
	
	/**
	 * 获取文件key
	 * @param ext  例如 .png  .jpg
	 * @param storageName  文件名存在是ext失效
	 * @return  fileKey
	 */
	public String localFileRelativeFileKey(String second, String ext, String storageName) {
		
		if (this.secondPositions == null) {
			log.debug("二级业务路径没有配置");
			throw new RuntimeException("二级业务路径没有配置");
		}
		
		// 二级路径
		String secondPosition = this.secondPositions.get(second);
		
		if (StringUtils.isEmpty(secondPosition)) {
			log.debug("路径没有配置: {}" + second);
			throw new RuntimeException("二级路径为空");
		}
		
		DateTime dt = new DateTime();
		// 获取年份
		int year = dt.getYear();
		int monthOfYear = dt.getMonthOfYear();
		
		// 构建文件key
		StringBuffer key = new StringBuffer();
		key.append("/");
		key.append(secondPosition);
		key.append("/");
		key.append(year);
		key.append("/");
		key.append(monthOfYear);
		key.append("/");
		
		if (StringUtils.isNotEmpty(storageName)) {
			key.append(storageName);
		} else {
			// 生成随机文件名
			String uuid32 = UuidUtil.uuid32();
			key.append(uuid32);
			if (StringUtils.isEmpty(ext)) {
				key.append(ext);
			}
		}
		
		return key.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
