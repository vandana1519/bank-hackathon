package com.hackathon.bankservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hackathon.bankservice.constant.AppConstants;
import com.hackathon.bankservice.dto.GenerateTokenDto;
import com.hackathon.bankservice.entity.Counter;
import com.hackathon.bankservice.entity.Customer;
import com.hackathon.bankservice.entity.Rating;
import com.hackathon.bankservice.entity.Services;
import com.hackathon.bankservice.entity.TokenDetail;
import com.hackathon.bankservice.exception.CustomerNotFoundException;
import com.hackathon.bankservice.exception.InvalidTokenException;
import com.hackathon.bankservice.service.BankService;

@WebMvcTest(value = BankController.class)
public class BankControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	BankService bankService;
	
	static Customer customer = new Customer();
	static Services service = new Services();
	static Counter counter = new Counter();
	static TokenDetail token = new TokenDetail();
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
	void generateTokenTestSuccess() throws Exception {
		
		Mockito.when(bankService.generateToken(Mockito.anyLong(), Mockito.anyLong())).thenReturn(generateTokenDto);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/generateToken").accept(MediaType.APPLICATION_JSON)
				.param("customerId", "1").param("serviceId", "1");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	void generateTokenTestException() throws Exception {
		
		Mockito.doThrow(CustomerNotFoundException.class).when(bankService).generateToken(10L, 1L);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/generateToken").accept(MediaType.APPLICATION_JSON)
				.param("customerId", "10").param("serviceId", "1");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), result.getResponse().getStatus());
	}
	
	@Test
	void availServiceTestSuccess() throws Exception {
		
		Mockito.doReturn(token.getStatus()).when(bankService).availService(Mockito.anyLong());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/service").accept(MediaType.APPLICATION_JSON)
				.param("tokenId", "1");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	@ExceptionHandler(InvalidTokenException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	void availServiceTestException() throws Exception {
		
		Mockito.doThrow(InvalidTokenException.class).when(bankService).availService(Mockito.anyLong());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/service").accept(MediaType.APPLICATION_JSON)
				.param("tokenId", "1");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), result.getResponse().getStatus());
	}
	
	@Test
	void receiveRatingTestSuccess() throws Exception {
		
		Mockito.doReturn(new String("Thankyou for your valuable feedback!!")).when(bankService).receiveRating(Mockito.anyLong(), Mockito.anyLong());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rating").accept(MediaType.APPLICATION_JSON)
				.param("tokenId", "1").param("ratingId", "1");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.ACCEPTED.value(), result.getResponse().getStatus());
	}
	
	@Test
	@ExceptionHandler(InvalidTokenException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	void receiveRatingTestException() throws Exception {
		
		Mockito.doThrow(InvalidTokenException.class).when(bankService).receiveRating(Mockito.anyLong(), Mockito.anyLong());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rating").accept(MediaType.APPLICATION_JSON)
				.param("tokenId", "1").param("ratingId", "1");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.EXPECTATION_FAILED.value(), result.getResponse().getStatus());
	}
}
