package com.hackathon.bankservice.service.impl;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

@SpringBootTest
public class BankServiceImplTest {

	@InjectMocks
	BankServiceImpl bankServiceImpl;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private TokenRepository tokenRepository;

	@Mock
	private ServiceRepository serviceRepository;

	@Mock
	private CounterRepository counterRepository;

	@Mock
	private RatingRepository ratingRepository;

	static TokenDetail token = new TokenDetail();

	static Services service = new Services();
	static Counter counter = new Counter();
	static Customer customer = new Customer();
	static GenerateTokenDto generateTokenDto = new GenerateTokenDto();
	static List<Long> tokenList = new ArrayList<>();
	static Rating rating = new Rating();

	@BeforeAll
	public static void setup() {
		customer.setCustomerId(1L);
		customer.setCustomerName("TestUser");
		customer.setAccountNumber(101L);

		service.setServiceId(1L);
		service.setServiceName("Cash Deposit");

		token.setTokenId(1L);
		token.setCreatedAt(LocalDateTime.now());
		token.setStatus(AppConstants.NEW);
		token.setRating(null);
		token.setService(service);
		token.setCustomer(customer);
		token.setCounter(counter);
		token.setUpdatedAt(LocalDateTime.now());

		tokenList.add(3L);
		tokenList.add(2L);

		generateTokenDto.setTokenId(1L);
		generateTokenDto.setCounterId(1L);
		
		rating.setRatingId(5L);
		rating.setRatingDesc("Excellent");
	}

	@Test
	public void testgenerateTokenForSuccess() throws CustomerNotFoundException {
		Mockito.when(customerRepository.findByCustomerId(Mockito.anyLong())).thenReturn(customer);
		Mockito.when(serviceRepository.findByServiceId(Mockito.anyLong())).thenReturn(service);
		Mockito.when(counterRepository.getCounter(Mockito.anyLong())).thenReturn(counter);
		Mockito.when(tokenRepository.save(token)).thenReturn(token);
		System.out.println("Token::" + token.getStatus());
		Mockito.doNothing().when(tokenRepository).updateTokenStatus(token.getStatus(), token.getTokenId());
		generateTokenDto = bankServiceImpl.generateToken(1L, 1L);
		Assertions.assertNotNull(generateTokenDto);
	}

	@Test
	public void testAvailServiceForSuccess() throws InvalidTokenException {
		Mockito.when(tokenRepository.findByTokenId(Mockito.anyLong())).thenReturn(token);
		Mockito.when(tokenRepository.getTokenIdList(Mockito.anyLong())).thenReturn(tokenList);
		Mockito.doNothing().when(tokenRepository).updateTokenStatus(token.getStatus(), token.getTokenId());
		Mockito.doNothing().when(tokenRepository).updateTokenStatus(token.getStatus(), token.getTokenId());
		String availServiceResponse = bankServiceImpl.availService(1L);
		Assertions.assertNotNull(availServiceResponse);

	}

	@Test
	@ExceptionHandler(InvalidTokenException.class)
	public void testAvailServiceForNullToken() throws InvalidTokenException {
		Mockito.when(tokenRepository.findByTokenId(Mockito.anyLong())).thenReturn(null);

		Assertions.assertThrows(InvalidTokenException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				String availServiceResponse = bankServiceImpl.availService(1L);
				//Assertions.assertNull(availServiceResponse);
				Assertions.assertEquals("Invalid Token.", availServiceResponse);
			}
		});

	}
	
	@Test
	@ExceptionHandler(InvalidTokenException.class)
	public void testAvailServiceForStatusInProgress() throws InvalidTokenException {
		Mockito.when(tokenRepository.findByTokenId(Mockito.anyLong())).thenReturn(token);
		token.setStatus(AppConstants.IN_PROGRESS);
		Assertions.assertThrows(InvalidTokenException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				String availServiceResponse = bankServiceImpl.availService(1L);
				//Assertions.assertNull(availServiceResponse);
				Assertions.assertEquals("Token is in Progress.", availServiceResponse);
			}
		});

	}
	
	@Test
	@ExceptionHandler(InvalidTokenException.class)
	public void testAvailServiceForStatusClosed() throws InvalidTokenException {
		Mockito.when(tokenRepository.findByTokenId(Mockito.anyLong())).thenReturn(token);
		token.setStatus(AppConstants.CLOSED);
		Assertions.assertThrows(InvalidTokenException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				String availServiceResponse = bankServiceImpl.availService(1L);
				//Assertions.assertNull(availServiceResponse);
				Assertions.assertEquals("Token already Serviced, generate new token if required.", availServiceResponse);
			}
		});

	}
	
	@Test
	public void testReceiveRatingForSuccess() throws InvalidTokenException {
		Mockito.when(tokenRepository.findByTokenId(Mockito.anyLong())).thenReturn(token);
		token.setStatus(AppConstants.CLOSED);
		Mockito.when(ratingRepository.findByRatingId(Mockito.anyLong())).thenReturn(rating);
		Mockito.doNothing().when(tokenRepository).updateTokenRating(rating.getRatingId(),token.getTokenId());
		String response = bankServiceImpl.receiveRating(1L, 5L);
		Assertions.assertEquals("Thank you for your valuable feedback!!", response);
	}
	
	@Test
	public void testReceiveRatingForTokenNew() throws InvalidTokenException {
		Mockito.when(tokenRepository.findByTokenId(Mockito.anyLong())).thenReturn(token);
		token.setStatus(AppConstants.NEW);
		Mockito.when(ratingRepository.findByRatingId(Mockito.anyLong())).thenReturn(rating);
		Mockito.doNothing().when(tokenRepository).updateTokenRating(rating.getRatingId(),token.getTokenId());
		String response = bankServiceImpl.receiveRating(1L, 5L);
		Assertions.assertEquals("Token is not closed yet", response);
	}
	
	@Test
	public void testReceiveRatingForTokenInProgress() throws InvalidTokenException {
		Mockito.when(tokenRepository.findByTokenId(Mockito.anyLong())).thenReturn(token);
		token.setStatus(AppConstants.IN_PROGRESS);
		Mockito.when(ratingRepository.findByRatingId(Mockito.anyLong())).thenReturn(rating);
		Mockito.doNothing().when(tokenRepository).updateTokenRating(rating.getRatingId(),token.getTokenId());
		String response = bankServiceImpl.receiveRating(1L, 5L);
		Assertions.assertEquals("Token is not closed yet", response);
	}
	
	@Test
	@ExceptionHandler(InvalidTokenException.class)
	public void testReceiveRatingForTokenNull() throws InvalidTokenException {
		Mockito.when(tokenRepository.findByTokenId(Mockito.anyLong())).thenReturn(null);
		
		Assertions.assertThrows(InvalidTokenException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				String response = bankServiceImpl.receiveRating(1L, 5L);
				Assertions.assertEquals("Invalid Token.", response);
			}
		});
		
		
	}
}
