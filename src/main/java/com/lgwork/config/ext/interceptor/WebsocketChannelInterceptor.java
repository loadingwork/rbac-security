package com.lgwork.config.ext.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;


/**
 * 
 * websocket通道拦截器
 * 
 * @author irays
 *
 */
public class WebsocketChannelInterceptor implements ChannelInterceptor {
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
        	// access authentication header(s)
//            Authentication user =  ; 
//            accessor.setUser(user);
        }
        return message;
	}

}
