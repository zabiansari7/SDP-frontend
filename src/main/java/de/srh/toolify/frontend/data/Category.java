package de.srh.toolify.frontend.data;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Category {

	private Long categoryId;

	private String categoryName;
	@JsonIgnore
    private Instant createdOn;
	@JsonIgnore
	private Instant updatedOn;
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Instant getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}
	
	public Instant getUpdatedOn() {
		return updatedOn;
	}
	
	public void setUpdatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
	}


	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
