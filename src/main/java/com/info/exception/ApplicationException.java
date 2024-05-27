package com.info.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	private int errorCode;
	private Date timestamp;
	private String message;

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ApplicationException(HttpStatus httpStatus, int errorCode, Date timestamp, String message) {
		super();
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.timestamp = timestamp;
		this.message = message;
	}

	
}
