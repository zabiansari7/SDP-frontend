package de.srh.toolify.frontend.data;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Address {
	private Long addressID;
	private String streetName;
	private int streetNumber;
	private String cityName;
	private int postCode;
	private User user;
	@JsonIgnore
	private Instant createdOn;
	@JsonIgnore
	private Instant updatedOn;
	
	public Address() {}
	
	public Address(Long addressID, String streetName, int streetNumber, String cityName, int postCode, User user) {
		this.addressID = addressID;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.cityName = cityName;
		this.postCode = postCode;
		this.user = user;
	}

	public Long getAddressID() {
		return addressID;
	}
	
	public void setAddressID(Long addressID) {
		this.addressID = addressID;
	}
	
	public String getStreetName() {
		return streetName;
	}
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	public int getStreetNumber() {
		return streetNumber;
	}
	
	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public int getPostCode() {
		return postCode;
	}
	
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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

	@JsonIgnore
	public String getAddressLineOne(){
		return this.getStreetName() + "-" + this.getStreetNumber();
	}
	@JsonIgnore
	public String getAddressLineTwo(){
		return this.getPostCode() + " - " + this.getCityName();
	}
}
