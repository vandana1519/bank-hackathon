package com.hackathon.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackathon.bankservice.entity.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long>{

	@Query(value = "SELECT * from counter where service_id= :serviceId ", nativeQuery = true)
	public Counter getCounter(@Param("serviceId") Long serviceId);
	
}
