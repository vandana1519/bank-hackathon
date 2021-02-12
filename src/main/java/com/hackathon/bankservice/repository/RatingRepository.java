package com.hackathon.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.bankservice.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>{
	
	public Rating findByRatingId(Long ratingId);

}
