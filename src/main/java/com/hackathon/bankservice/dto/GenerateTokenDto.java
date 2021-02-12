package com.hackathon.bankservice.dto;

public class GenerateTokenDto {

	private Long tokenId;
	private Long counterId;
	
	public GenerateTokenDto() {}
	
	public GenerateTokenDto(Long tokenId, Long counterId) {
		super();
		this.tokenId = tokenId;
		this.counterId = counterId;
	}
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

	@Override
	public String toString() {
		return "GenerateTokenDto [tokenId=" + tokenId + ", counterId=" + counterId + "]";
	}	
	
}
