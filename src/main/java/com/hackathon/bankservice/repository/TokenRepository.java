package com.hackathon.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hackathon.bankservice.entity.TokenDetail;

@Repository
public interface TokenRepository extends JpaRepository<TokenDetail,Long> {

	public TokenDetail findByTokenId(Long tokenId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE Token t set t.status = :status WHERE t.token_id = :tokenId", nativeQuery=true)
	void updateTokenStatus(String status, Long tokenId);
}
