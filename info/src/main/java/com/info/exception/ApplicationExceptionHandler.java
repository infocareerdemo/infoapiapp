package com.info.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ApplicationException> globalExceptionHandler(ApplicationException ex){
		return new ResponseEntity<ApplicationException>(ex,ex.getHttpStatus());
	}
	
	@ExceptionHandler(ApplicationRunTimeException.class)
	public ResponseEntity<ApplicationRunTimeException> globalExceptionHandler(ApplicationRunTimeException  ex){
		return new ResponseEntity<ApplicationRunTimeException>(ex,ex.getHttpStatus());
	}
	

}
