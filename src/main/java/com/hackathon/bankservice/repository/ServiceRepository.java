package com.hackathon.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.bankservice.entity.Services;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {

	public Services findByServiceId(Long serviceId);
	
}
