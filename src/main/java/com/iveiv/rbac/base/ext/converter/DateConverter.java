package com.iveiv.rbac.base.ext.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.slf4j.Slf4j;


/**
 * 时间格式转换
 * @author irays
 *
 */
@Slf4j
public class DateConverter  implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date newDate = dateTime.parse(source);
			return newDate;
		} catch (Exception e) {
			log.debug("时间格式yyyy-MM-dd HH:mm:ss格式化失败: {}", source);
			try {
				dateTime = new SimpleDateFormat("yyyy-MM-dd");
				return dateTime.parse(source);
			} catch (Exception e2) {
				log.debug("时间格式yyyy-MM-dd格式化失败: {}", source);
			}
		}
		
		return null;
	}

	

}
