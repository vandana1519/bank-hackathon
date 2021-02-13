package com.hackathon.bankservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.bankservice.constant.AppConstants;
import com.hackathon.bankservice.dto.GenerateTokenDto;
import com.hackathon.bankservice.entity.Counter;
import com.hackathon.bankservice.entity.Customer;
import com.hackathon.bankservice.entity.Rating;
import com.hackathon.bankservice.entity.Services;
import com.hackathon.bankservice.entity.TokenDetail;
import com.hackathon.bankservice.exception.CustomerNotFoundException;
import com.hackathon.bankservice.exception.InvalidTokenException;
import com.hackathon.bankservice.repository.CounterRepository;
import com.hackathon.bankservice.repository.CustomerRepository;
import com.hackathon.bankservice.repository.RatingRepository;
import com.hackathon.bankservice.repository.ServiceRepository;
import com.hackathon.bankservice.repository.TokenRepository;
import com.hackathon.bankservice.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private CounterRepository counterRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public GenerateTokenDto generateToken(Long customerId, Long serviceId) throws CustomerNotFoundException {
		GenerateTokenDto generateTokenDto = new GenerateTokenDto();
		Customer customer = customerRepository.findByCustomerId(customerId);

		if (customer == null) {
			throw new CustomerNotFoundException("Customer not Registered!!!");
		}

		Services service = serviceRepository.findByServiceId(serviceId);
		Counter counter = counterRepository.getCounter(serviceId);

		if (customer != null) {

			TokenDetail token = new TokenDetail();
			token.setCreatedAt(LocalDateTime.now());
			token.setStatus(AppConstants.NEW);
			token.setRating(null);
			token.setService(service);
			token.setCustomer(customer);
			token.setCounter(counter);
			token.setUpdatedAt(LocalDateTime.now());
			TokenDetail generatedToken = tokenRepository.save(token);
			tokenRepository.updateTokenStatus(token.getStatus(), generatedToken.getTokenId());
			generateTokenDto.setCounterId(counter.getCounterId());
			generateTokenDto.setTokenId(generatedToken.getTokenId());
		}

		return generateTokenDto;
	}

	@Override
	public String availService(Long tokenId) throws InvalidTokenException {

		TokenDetail tokenDetail = tokenRepository.findByTokenId(tokenId);

		// For now we are just updating the status and in the near future we will
		// implement any validation required.
		if (tokenDetail == null) {
			throw new InvalidTokenException("Invalid Token.");
		}

		if (tokenDetail.getStatus().equals(AppConstants.IN_PROGRESS)) {
			throw new InvalidTokenException("Token is in Progress.");
		}

		if (tokenDetail.getStatus().equals(AppConstants.CLOSED)) {
			throw new InvalidTokenException("Token already Serviced, generate new token if required.");
		}
		List<Long> tokenList = tokenRepository.getTokenIdList(tokenDetail.getService().getServiceId());
		for (Long token : tokenList) {
			if (tokenId > token) {
				throw new InvalidTokenException("Token can't be serviced at the moment.");
			}
		}
		tokenDetail.setStatus(AppConstants.IN_PROGRESS);
		tokenRepository.updateTokenStatus(tokenDetail.getStatus(), tokenId);

		tokenDetail.setStatus(AppConstants.CLOSED);
		tokenRepository.updateTokenStatus(tokenDetail.getStatus(), tokenId);

		return tokenDetail.getStatus();
	}

	@Override
	public String receiveRating(Long tokenId, Long ratingId) throws InvalidTokenException {

		TokenDetail tokenDetail = tokenRepository.findByTokenId(tokenId);
		String response = "";
		if (tokenDetail == null) {
			throw new InvalidTokenException("Invalid Token.");
		}
		if (tokenDetail != null) {
			if (tokenDetail.getStatus().equals(AppConstants.CLOSED)) {
				Rating rating = ratingRepository.findByRatingId(ratingId);
				// tokenDetail.setRating(rating);
				tokenRepository.updateTokenRating(rating.getRatingId(), tokenId);
				response = "Thank you for your valuable feedback!!";
			} else {
				response = "Token is not closed yet";
			}
		}
		return response;
	}

}
