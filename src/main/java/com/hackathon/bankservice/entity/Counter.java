package com.hackathon.bankservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Counter")
public class Counter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long counterId;

	@OneToOne()
	@JoinColumn(name = "service_id", referencedColumnName = "serviceId")
	private Services service;

	public Counter() {}
	
	public Counter(Long counterId, Services service) {
		super();
		this.counterId = counterId;
		this.service = service;
	}

	public Long getCounterId() {
		return counterId;
	}

	public void setCounterId(Long counterId) {
		this.counterId = counterId;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}

	
	
}
