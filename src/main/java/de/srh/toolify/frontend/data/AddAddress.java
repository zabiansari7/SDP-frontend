package de.srh.toolify.frontend.data;

public class AddAddress {
	
	private Long addressID;
	private String streetName;
	private int streetNumber;
	private String cityName;
	private int postCode;
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
	public UserForAddress getUser() {
		return user;
	}
	public void setUser(UserForAddress user) {
		this.user = user;
	}
	
	
	

}
