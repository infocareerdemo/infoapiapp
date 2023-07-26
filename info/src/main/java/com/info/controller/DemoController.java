package com.info.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller

/*
==========================================================================================
@Controller is used to mark classes as Spring MVC Controller.
It is a specialized version of @Component annotation.
In @Controller, we can return a view in Spring Web MVC.
@Controller annotation indicates that the class is a “controller” like a web controller.
In @Controller, we need to use @ResponseBody on every handler method.
It was added to Spring 2.5 version.
==========================================================================================
*/

@RequestMapping("/demo")
public class DemoController {

	@GetMapping("/controller")
	@ResponseBody
	public String sampleDemo() {
		return "This is Demo Sample Controller normal controller";
	}



}
