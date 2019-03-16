package com.lgwork.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


/**
 * 
 * websocket配置
 * 
 * 
 * 
 * @author irays
 *  //@EnableWebSocket
 *  //WebSocketMessageBrokerConfigurer
 *  AbstractSecurityWebSocketMessageBrokerConfigurer
 *  WebSocketConfigurer
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	
	
	
	/**
	 * 注册stomp的端点
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		registry.addEndpoint("/wserver").withSockJS().setWebSocketEnabled(true);
	}
	
	
	/**
	 *  配置信息代理
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		// 订阅Broker名称
		// "/queue", "/topic",
        registry.enableSimpleBroker("/queue/qrlogin");
        // 全局使用的消息前缀（客户端订阅路径上会体现出来） 
        registry.setApplicationDestinationPrefixes("/app"); 
//      点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
//      registry.setUserDestinationPrefix("/user/");
        
        // registry.enableStompBrokerRelay("/topic", "/queue").setRelayHost("rabbit.someotherserver").setRelayPort(62623).setClientLogin("marcopolo").setClientPasscode("letmein01");

        
     // "/topic/system/notifications"
     // "/queue/system/notifications"
     // "/queue/qr/login"
        
	}
	
	

	@Override
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		
		//  "/topic/system/notifications"
		// registry.addHandler(myHandler(), "/myHandler").setAllowedOrigins("http://mydomain.com");
		
		messages
		.simpDestMatchers("/user/**").authenticated()
//        .nullDestMatcher().authenticated()
//        .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.DISCONNECT).permitAll()
        .simpSubscribeDestMatchers("/user/queue/errors").permitAll() 
		.simpDestMatchers("/app/**").permitAll()
		.simpDestMatchers("/wserver/**").permitAll();
//        .simpDestMatchers("/app/**").hasRole("USER") 
//        .simpSubscribeDestMatchers("/user/**", "/topic/friends/*").hasRole("USER") 
//        .simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll() 
	}
	
	
	/**
	 * Disable CSRF within WebSockets
	 * @href  https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#websocket
	 */
	@Override
    protected boolean sameOriginDisabled() {
        return true;
    }
	
	

}

