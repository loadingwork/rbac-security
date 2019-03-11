package com.iveiv.rbac.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.iveiv.rbac.api.annotation.TokenCurrentUserArgumentResolver;
import com.iveiv.rbac.api.interceptor.TokenLoginInterceptor;
import com.iveiv.rbac.base.ext.converter.DateConverter;
import com.iveiv.rbac.base.ext.formatter.SysMenuTypeEnumFormatter;
import com.iveiv.rbac.config.ext.mvc.DictTransformReturnValueHandler;


/**
 * 
 * 
 * 
 * @author irays
 *
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
	
	
	/**
	 * 定义restTemple
	 * @param restTemplateBuilder
	 * @return
	 */
	@Autowired
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		WebMvcConfigurer.super.addCorsMappings(registry);
		registry.addMapping("/api/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*");
		registry.addMapping("/token/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		
		// 排除拦截地址
		String[] excludeLoginFilter = new String[] {
				"/api/auth/index",
				"/api/auth/login",
				"/api/auth/logout",
				"/api/auth/refresh",
				"/api/auth/autoVerify",
				
				"/api/auth/qrcodeLoginByScan",
				"/api/auth/qrcodeConfirmLogin",
				
				"/api/token/rememberme/autoLogin",
				"/api/token/rememberme/refresh"
		};
		
		// 登录拦截地址
		String[] tokenPathFilter = new String[] {
				"/api/**"
		};
		
		registry.addInterceptor(tokenLoginInterceptor()).addPathPatterns(tokenPathFilter).excludePathPatterns(excludeLoginFilter);
		
	}
	
	
	/**
	 * 自定义参数处理
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
		
		// 注册用户信息的参数分解器,  必须在登录拦截器下使用
		resolvers.add(tokenCurrentUserArgumentResolver());
	}
	
	
	/**
	 * 自定义返回值处理
	 */
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		
		// 添加自定义返回值处理
		handlers.add(dictTransformReturnValueHandler());
		
		WebMvcConfigurer.super.addReturnValueHandlers(handlers);
	}
	
	/**
	 * 用户登录拦截器
	 * @return
	 */
	@Bean
	public TokenLoginInterceptor tokenLoginInterceptor() {
		return new TokenLoginInterceptor();
	}
	
	/**
	 * 参数解析器
	 * @return
	 */
	@Bean
	public TokenCurrentUserArgumentResolver tokenCurrentUserArgumentResolver() {
		return new TokenCurrentUserArgumentResolver();
	}
	
	/**
	 * 自定数据字典处理
	 * @return
	 */
	@Bean
	public DictTransformReturnValueHandler dictTransformReturnValueHandler() {
		return new DictTransformReturnValueHandler();
	}
	
	
	/**
	 * 由于启用了自定义配置
	 * EnableWebMvcConfiguration 配置已经失效
	 * 
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		 
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
	        
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	
	/**
	 * 添加自定义
	 * 
	 * Converter
	 * Formatter
	 * @InitBinder
	 * 
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		WebMvcConfigurer.super.addFormatters(registry);
		
		// 添加自定义数据转换
		registry.addConverter(new DateConverter());
		
		// 添加自定义format
		registry.addFormatter(new SysMenuTypeEnumFormatter());
		
	}
	
	

}

