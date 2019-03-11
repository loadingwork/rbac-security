package com.iveiv.rbac.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;


/**
 * 
 * @TODO 暂时不实现
 * 
 * @author irays
 *
 */
//@Component
@Order(4)
@Aspect
public class DictTransformAop {
	
	
	@Pointcut("execution(* com.iveiv.rbac.sys.service.**.*(..))")
	public void sysService() {
	}
	
	
	@Around(value = "sysService()", argNames="pjp")
	public Object afterReturn(ProceedingJoinPoint pjp) {
		
		return null;
	}
	

}
