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
import com.hackathon.bankservice.exception.InvalidTokenException;
import com.hackathon.bankservice.service.BankService;

@Validated
@RestController
public class BankController {

	@Autowired
	private BankService bankService;

	@PostMapping("/generateToken")
	public ResponseEntity generateToken(@RequestParam Long customerId, @RequestParam Long serviceId)
			throws CustomerNotFoundException {
		GenerateTokenDto generateTokenDto = null;

		try {
			generateTokenDto = bankService.generateToken(customerId, serviceId);
		} catch (CustomerNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<GenerateTokenDto>(generateTokenDto, HttpStatus.CREATED);
	}

	@PostMapping("/service")
	public ResponseEntity<String> availService(@RequestParam Long tokenId) throws InvalidTokenException {
		String availServiceResponse = "";
		try {
			availServiceResponse = bankService.availService(tokenId);
		} catch (InvalidTokenException e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(availServiceResponse, HttpStatus.OK);
	}

	@PutMapping("/rating")
	public ResponseEntity receiveRating(@RequestParam Long tokenId, @RequestParam Long ratingId) {
		String ratingResponse = "";
		try {
			ratingResponse = bankService.receiveRating(tokenId, ratingId);
		} catch (InvalidTokenException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(ratingResponse, HttpStatus.ACCEPTED);

	}
}
