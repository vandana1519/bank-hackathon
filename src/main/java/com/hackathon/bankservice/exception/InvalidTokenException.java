package com.hackathon.bankservice.exception;

public class InvalidTokenException extends Exception {

	private static final long serialVersionUID = 4073428868108678532L;

	public InvalidTokenException(String message) {
		super(message);
	}
	
	
}
