package de.srh.toolify.frontend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AddAddress {
	
	@JsonIgnore
	private Long addressID;
	private String streetName;
	private String streetNumber;
	private String cityName;
	private String postcode;
	UserForAddress user;
	
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
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPostCode() {
		return postcode;
	}
	public void setPostCode(String postCode) {
		this.postcode = postCode;
	}
	public UserForAddress getUser() {
		return user;
	}
	public void setUser(UserForAddress user) {
		this.user = user;
	}
	
	
	

}
