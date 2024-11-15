package com.sri.Exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(Exception.class)
	public String getExceptionPage(Exception e) {
		System.out.println("Exception in innn    ");
		return "error-404";
	}
	
	
}
