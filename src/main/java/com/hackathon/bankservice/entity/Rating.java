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
@Table(name = "Rating")
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ratingId;
	private String ratingDesc;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "token_id", referencedColumnName = "tokenId")
	private TokenDetail token;

	public Rating() {}
	
	public Rating(Long ratingId, String ratingDesc) {
		super();
		this.ratingId = ratingId;
		this.ratingDesc = ratingDesc;
	}
	public Long getRatingId() {
		return ratingId;
	}
	public void setRatingId(Long ratingId) {
		this.ratingId = ratingId;
	}
	public String getRatingDesc() {
		return ratingDesc;
	}
	public void setRatingDesc(String ratingDesc) {
		this.ratingDesc = ratingDesc;
	}

}
