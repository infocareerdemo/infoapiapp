package com.info.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.service.LogService;

@RestController
public class LogController {

	@Autowired
	private LogService logService;

	@GetMapping("/changeloglevel")
	public String changeLogLevel(@RequestParam(required = false) String loggerName,
			@RequestParam(required = false) String level) {
		return logService.setLogLevel(loggerName, level);
	}

}
