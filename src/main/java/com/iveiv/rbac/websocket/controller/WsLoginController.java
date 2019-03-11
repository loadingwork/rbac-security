package com.iveiv.rbac.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;


/**
 * 
 * ws用户登录控制器
 * 
 * @author irays
 *
 */
@Controller
public class WsLoginController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	
	
	@MessageMapping("/hello")
    @SendTo("/topic/one")
    public String sendDemo(String message) {
		
		simpMessagingTemplate.convertAndSend("/topic/one/aaaaaaaa","sendDemo ---> sendDemo");
		
		simpMessagingTemplate.convertAndSendToUser("admin","/topic/one","发给特定的用户");
		
        return message;
    }

	@SendToUser("/user/one")
	public void tokenToUser() {
		System.out.println("tokenToUser");
	}
	
    
    
    

	@MessageExceptionHandler
	public void handleExceptions(Throwable t) {
		t.printStackTrace();
	}
	

}
