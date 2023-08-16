package com.info.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class LogService{

	public String setLogLevel(String loggerName, String level) {
		ch.qos.logback.classic.Logger root = getLogger(loggerName);
		return setLogLevel(level, root);
	}

	private String setLogLevel(String level, ch.qos.logback.classic.Logger root) {
		if ("info".equalsIgnoreCase(level)) {
			root.setLevel(ch.qos.logback.classic.Level.INFO);
		} else if ("debug".equalsIgnoreCase(level)) {
			root.setLevel(ch.qos.logback.classic.Level.DEBUG);
		} else if ("warn".equalsIgnoreCase(level)) {
			root.setLevel(ch.qos.logback.classic.Level.WARN);
		} else if ("trace".equalsIgnoreCase(level)) {
			root.setLevel(ch.qos.logback.classic.Level.TRACE);
		} else if ("error".equalsIgnoreCase(level)) {
			root.setLevel(ch.qos.logback.classic.Level.ERROR);
		} else if ("off".equalsIgnoreCase(level)) {
			root.setLevel(ch.qos.logback.classic.Level.OFF);
		} else {
			root.setLevel(ch.qos.logback.classic.Level.ALL);
			level = "all";
		}
		return "logger level set as " + level;
	}

	private ch.qos.logback.classic.Logger getLogger(String loggerName) {
		ch.qos.logback.classic.Logger root;
		if (loggerName != null && !loggerName.isEmpty())
			root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(loggerName);
		else
			root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
		return root;
	}

}
