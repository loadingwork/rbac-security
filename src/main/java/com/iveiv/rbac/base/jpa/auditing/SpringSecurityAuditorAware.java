package com.iveiv.rbac.base.jpa.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;


/**
 * 
 * 获取审计主体用户
 * 
 *  @CreatedBy  获取用户
 * @author irays
 *
 */
public class SpringSecurityAuditorAware implements  AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		
		Optional<String> result = Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
		    .filter(Authentication::isAuthenticated)
		    .map(Authentication::getPrincipal)
		    .map(User.class::cast)
		    .map(User::getUsername);
		
		return result;
	}

}
