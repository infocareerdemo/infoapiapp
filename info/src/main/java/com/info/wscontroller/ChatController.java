package com.info.wscontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.info.wsentity.WebSocketMsg;


@RestController
public class ChatController {

	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /*@MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }
    
    @MessageMapping("/topic-message")
    public Message groupMessage(@Payload Message message) {
    	simpMessagingTemplate.convertAndSendToUser(message.getGroupId(),"/topic",message);
    	System.out.println(message.toString());
    	return message;
    }*/
    
    
    
    
    
    //created by anu
    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody WebSocketMsg webSocketMsg) {
    	simpMessagingTemplate.convertAndSend("/topic/message", webSocketMsg);
	       return new ResponseEntity<>(HttpStatus.OK);
     }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload WebSocketMsg webSocketMsg) {
	
      }

    @SendTo("/topic/message")
    public WebSocketMsg broadcastMessage(@Payload WebSocketMsg webSocketMsg) {
	  return webSocketMsg;
     }
    
 
}