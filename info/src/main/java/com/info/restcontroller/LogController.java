package com.info.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.info.service.LogService;

@RestController
@RequestMapping("/demolog")
public class LogController {

	@Autowired
	private LogService logService;

	private static final Logger logger = LoggerFactory.getLogger(LogController.class);

	// get -
	// http://localhost:8080/demolog/changeloglevel?loggerName=com.info&level=warn
	// -- dynamically change the logging level in application
	@GetMapping("/changeloglevel")
	public String changeLogLevel(@RequestParam(required = false) String loggerName,
			@RequestParam(required = false) String level) {
		return logService.setLogLevel(loggerName, level);
	}

	// http://localhost:8080/demolog/console -- for print logs on console
	@GetMapping("/consolelog")
	public String logs() {

		// Your application code here
		logger.info("This is an info message.");
		logger.error("This is an error message.");
		logger.debug("This is a debug message.");
		logger.warn("This is a warn message.");
		return "logs";
	}

	// http://localhost:8080/demolog/emaillog -- for explicitly sending logs to
	// email
	@GetMapping("/emaillog")
	public String emailLogger() {
		Logger log = LoggerFactory.getLogger(LogController.class);
		log.debug("This is a DEBUG message");
		log.info("This is an INFO message");
		log.warn("This is a WARN message");
		log.error("This is an ERROR message");
		return "Success";
	}

	// below urls are testing logging mechanism
	@GetMapping("/message")

	public String getMessage() {

		int i = 10;
		int j = 0;
		j = i / 0;

		logger.info("[getMessage] info message");
		logger.warn("[getMessage] warn message");
		logger.error("[getMessage] error message");
		logger.debug("[getMessage] debug message");
		logger.trace("[getMessage] trace message");

		System.out.println("<<<--- j=i/20 error --->>>");

		return "open console to check log messages.";

	}

//		http://localhost:8080/demolog/debug
	@GetMapping("/debug")
	public String greeting() {

		logger.debug("debug message from controller");
		return "Debug Logger Controller";

	}

//		http://localhost:8080/demolog/info
	@GetMapping("/info")
	public String info() {
		logger.info("info msg from controller");
		return "Info Logger Controller";

	}

//		http://localhost:8080/demolog/warn
	@GetMapping("/warn")
	public String warn() {

		logger.warn("waring msg from controller");
		return "Warn Logger Controller";

	}

//		http://localhost:8080/demolog/erro
	@GetMapping("/error")
	public String errorLogger() {
		logger.error("TESTING the ERROR in our log file");
		return "Error Logger Controller";

	}

//		http://localhost:8080/demolog/trace
	@GetMapping("/trace")
	public String traceLoger() throws InterruptedException {

		logger.trace("trace message from controller");
		return "Trace Controller";

	}

//		http://localhost:8080/demolog/wait
	@GetMapping("/wait")
	public String waiting() throws InterruptedException {
		logger.wait(67500000);
		return "Wait Controller";

	}

}
