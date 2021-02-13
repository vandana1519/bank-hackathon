package com.hackathon.bankservice.service;

import com.hackathon.bankservice.dto.GenerateTokenDto;
import com.hackathon.bankservice.exception.CustomerNotFoundException;
import com.hackathon.bankservice.exception.InvalidTokenException;

public interface BankService {
	
	public GenerateTokenDto generateToken(Long customerId, Long serviceId) throws CustomerNotFoundException;
	
	public String availService(Long tokenId) throws InvalidTokenException;
	
	public String receiveRating(Long tokenId, Long ratingId) throws InvalidTokenException;

}
