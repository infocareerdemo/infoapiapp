package com.info.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/java8")
public class DemoJava8 {
	
	@GetMapping("/welcome")
	private String getSampleDemo() {
		return "Welcome to Java 8";
	}
	

}
