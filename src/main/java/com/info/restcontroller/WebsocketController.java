package com.info.restcontroller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.info.entity.Users;

@Controller
public class WebsocketController {

@MessageMapping("/userUpdated")
@SendTo("/topic/userUpdated")
public Users userUpdated(Users updatedUser) {
    // Handle the WebSocket message here
    // You can process the updated user data and return it if needed
	   System.out.println("Received WebSocket message: " + updatedUser);
    return updatedUser;
}
}