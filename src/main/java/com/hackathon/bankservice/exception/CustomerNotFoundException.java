package com.hackathon.bankservice.exception;

public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = 4073428868108678532L;

	public CustomerNotFoundException(String message) {
		super(message);
	}
	
}
