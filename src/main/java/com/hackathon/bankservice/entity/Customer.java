package com.hackathon.bankservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Customer_Details")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	private String customerName;
	private Long accountNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "token_id", referencedColumnName = "tokenId")
	private TokenDetail token;
	
	public Customer() {}
	
	public Customer(Long customerId, String customerName, Long accountNumber) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.accountNumber = accountNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	

	
}
