package com.hackathon.bankservice.dto;

public class GenerateTokenDto {

	private Long tokenId;
	private Long counterId;
	
	public Long getTokenId() {
		return tokenId;
	}
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	public Long getCounterId() {
		return counterId;
	}
	public void setCounterId(Long counterId) {
		this.counterId = counterId;
	}
	
}
