package com.lgwork.websocket.handle;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * socket 测试
 * @author irays
 *
 */
public class MyHandler extends TextWebSocketHandler {
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		
		TextMessage textMessage = new TextMessage("1111");
		
		session.sendMessage(textMessage);
		
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		System.err.println(message);
		
	}

}
