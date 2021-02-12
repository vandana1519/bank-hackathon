package com.hackathon.bankservice.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Token")
public class TokenDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rating_id", referencedColumnName = "ratingId")
	private Rating rating;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "counter_id", referencedColumnName = "counterId")
	private Counter counter;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "service_id", referencedColumnName = "serviceId")
	private Services service;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id", referencedColumnName = "customerId")
	private Customer customer;
	
	private String status;
	
	public TokenDetail() {}

	public TokenDetail(String status, LocalDateTime createdAt, LocalDateTime updatedAt, Services service,
			Customer customer, Rating rating) {
		super();
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.service = service;
		this.customer = customer;
		this.rating = rating;
	}

	public Counter getCounter() {
		return counter;
	}

	public void setCounter(Counter counter) {
		this.counter = counter;
	}

	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
}
