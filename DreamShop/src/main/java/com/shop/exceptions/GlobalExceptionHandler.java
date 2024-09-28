package com.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shop.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse> accessdeniedexceptionhandler(AccessDeniedException ex){
	       
		String msg = "You Can Not Access This Resource !";
		return new ResponseEntity<ApiResponse>(new ApiResponse(msg,ex.getMessage()),
				HttpStatus.FORBIDDEN);
	}
	
	
	

	
}
