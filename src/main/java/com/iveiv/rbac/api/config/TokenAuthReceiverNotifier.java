package com.iveiv.rbac.api.config;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.iveiv.rbac.api.event.RememberMeLoginSuccessEvent;
import com.iveiv.rbac.api.event.RememberMeRefreshEvent;
import com.iveiv.rbac.api.event.TokenRefreshEvent;
import com.iveiv.rbac.api.event.UserLoginSuccessEvent;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 监听用户授权事件
 * 
 * @author irays
 *
 */
@Slf4j
@Component
public class TokenAuthReceiverNotifier {
	
	/**
	 * 用户登录事件监听
	 * @param userLoginSuccessEvent
	 */
	@EventListener( classes= {UserLoginSuccessEvent.class} )
	public void userLoginSuccessEventListener(UserLoginSuccessEvent userLoginSuccessEvent) {
		log.debug("登录成功事件: {}", userLoginSuccessEvent);
		
	}
	
	/**
	 * 用户token刷新
	 * @param tokenRefreshEvent
	 */
	@EventListener( classes= {TokenRefreshEvent.class} )
	public void tokenRefreshEventListener(TokenRefreshEvent tokenRefreshEvent) {
		log.debug("token刷新: {}", tokenRefreshEvent);
	}
	
	/**
	 *  app记住密码登录
	 * @param rememberMeLoginSuccessEvent
	 */
	@EventListener( classes= {RememberMeLoginSuccessEvent.class} )
	public void rememberMeLoginSuccessEventListener(RememberMeLoginSuccessEvent rememberMeLoginSuccessEvent) {
		log.debug("app记住密码登录: {}", rememberMeLoginSuccessEvent);
		
	}
	
	/**
	 * 记住登录信息刷新
	 * @param rememberMeRefreshEvent
	 */
	@EventListener( classes= {RememberMeRefreshEvent.class} )
	public void rememberMeRefreshEventListener(RememberMeRefreshEvent rememberMeRefreshEvent) {
		log.debug("app记住登录信息刷新: {}", rememberMeRefreshEvent);
		
	}
	
	

}
