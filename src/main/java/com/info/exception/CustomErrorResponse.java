package com.info.exception;

import java.util.Map;

import lombok.Data;

@Data
public class CustomErrorResponse {

	private String message;
	private Map<String, String> errors;

	public CustomErrorResponse(String message, Map<String, String> errors) {
		this.message = message;
		this.errors = errors;
	}

}
