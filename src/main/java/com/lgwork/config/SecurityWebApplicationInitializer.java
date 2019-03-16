package com.lgwork.config;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * 
 * AbstractSecurityWebApplicationInitializer with Spring MVC
 * 
   *     开启spring security空间
 * springSecurityFilterChain
 * 
 * @author irays
 *
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		super.beforeSpringSecurityFilterChain(servletContext);
//		 Placing MultipartFilter before Spring Security  配置拦截器在security之前
//		insertFilters(servletContext, new MultipartFilter());
	}

}