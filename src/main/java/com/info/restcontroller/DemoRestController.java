package com.info.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/*
===============================================================================
@RestController annotation is a special controller used in RESTful Web services,
 	and it’s the combination of @Controller and @ResponseBody annotation.
It is a specialized version of @Controller annotation.
In @RestController, we can not return a view.
@RestController annotation indicates that class is a controller
 	where @RequestMapping methods assume @ResponseBody semantics by default.
In @RestController, we don’t need to use @ResponseBody on every handler method.
It was added to Spring 4.0 version.
================================================================================
*/

@RestController
@RequestMapping("/demorest")

public class DemoRestController {

	@GetMapping("/restcontroller")
	private String getSampleDemo() {
		return "This is Demo Sample Rest Controller";
	}

	@PostMapping("/restcontroller/apiresponse")
	private Integer getDemoData() {
		return 1000;
	}
	
	@PostMapping("/restpost/postdata")
	private Integer getDemoPostData() {
		return 1000;
	}

}