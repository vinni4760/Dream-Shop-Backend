package com.shop.exceptions;

import org.springframework.stereotype.Component;

public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String msg) {
		super(msg);
 	}
       
	
}
