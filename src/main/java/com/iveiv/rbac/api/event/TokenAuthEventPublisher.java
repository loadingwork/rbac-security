package com.iveiv.rbac.api.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * 
 * token认证发布事件
 * 
 * 配置bean
 * 
 * @TODO 添加特殊处理
 * 
 * @author irays
 *
 */
public class TokenAuthEventPublisher  implements ApplicationEventPublisherAware, ApplicationEventPublisher {
	
	/**
	 * 发布事件对象
	 */
	private ApplicationEventPublisher eventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher; 
	}

	@Override
	public void publishEvent(Object event) {
		// 发布事件
		this.eventPublisher.publishEvent(event);
	}

	

}
