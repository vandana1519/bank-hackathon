package com.hackathon.bankservice.service;

import com.hackathon.bankservice.dto.GenerateTokenDto;
import com.hackathon.bankservice.exception.CustomerNotFoundException;

public interface BankService {
	
	public GenerateTokenDto generateToken(Long customerId, Long serviceId) throws CustomerNotFoundException;
	
	public String availService(Long tokenId);
	
	public String receiveRating(Long tokenId, Long ratingId);

}
