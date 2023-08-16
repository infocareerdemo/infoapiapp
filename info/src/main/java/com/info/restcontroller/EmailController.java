package com.info.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ch.qos.logback.classic.pattern.ThrowableProxyConverter;

//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/email")
public class EmailController extends ThrowableProxyConverter  {
	
	@GetMapping("/loggerMail")
	public String loggerMail() {
		Logger log = LoggerFactory.getLogger(EmailController.class);
        log.debug("This is a DEBUG message");
        log.info("This is an INFO message");
        log.warn("This is a WARN message");
        log.error("This is an ERROR message");
		return "Success";
	}
	
	

}
