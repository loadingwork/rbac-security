package com.iveiv.rbac.base.ext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;


/**
 * 获取spring bean对象管理上下文
 * @author irays
 *
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {
	
	/**
	 * bean管理上下文
	 */
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.debug("初始化bean管理上下文");
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	
	/**
	 * 根据bean获取
	 * @param name
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, @Nullable Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}
	
	
	
	

}
