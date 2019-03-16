package com.lgwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.lgwork.base.jpa.auditing.SpringSecurityAuditorAware;

/**
 * 
 * 审计配置
 * 
 * @author irays
 *
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class AuditingConfig {

	
	/**
	 * 获取审计用户名
	 * @return
	 */
	@Bean
	public AuditorAware<String> auditorProvider() {
		return new SpringSecurityAuditorAware();
	}

}
