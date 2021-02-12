package com.hackathon.bankservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.bankservice.dto.GenerateTokenDto;
import com.hackathon.bankservice.exception.CustomerNotFoundException;
import com.hackathon.bankservice.service.BankService;

@Validated
@RestController
public class BankController {
	
	@Autowired
	private BankService bankService;

	@PostMapping("/generateToken")
	public ResponseEntity generateToken(@RequestParam Long customerId,@RequestParam Long serviceId) {
		GenerateTokenDto generateTokenDto = null;
		try {
			generateTokenDto = bankService.generateToken(customerId, serviceId);
		} catch (CustomerNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(generateTokenDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/service")
	public ResponseEntity<String> availService(@RequestParam Long tokenId) {
		
		return new ResponseEntity<>(bankService.availService(tokenId),HttpStatus.OK);
	}
	
	@PutMapping("/rating")
	public ResponseEntity<String> receiveRating(@RequestParam Long tokenId, @RequestParam Long ratingId) {
		
		return new ResponseEntity<>(bankService.receiveRating(tokenId, ratingId),HttpStatus.ACCEPTED);
	}

}
