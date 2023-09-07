package com.info.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.service.LogService;

@RestController
public class LogController {

	@Autowired
	private LogService logService;
	
	private static final Logger logger = LoggerFactory.getLogger(LogController.class);

	@GetMapping("/changeloglevel")
	public String changeLogLevel(@RequestParam(required = false) String loggerName,
			@RequestParam(required = false) String level) {
		return logService.setLogLevel(loggerName, level);
	}
	
	@GetMapping("/log")
	public String logs() {

		// Your application code here
		logger.info("This is an info message.");
		logger.error("This is an error message.");
		logger.debug("This is a debug message.");
		logger.warn("This is a warn message.");
		return "logs";
	}

}
