package de.srh.toolify.frontend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private String password;
    
    @JsonIgnore
    private String repeatPassword;
    private String defaultStreetName;
    private Long defaultStreetNumber;
    private Long defaultPincode;
    private String defaultCity;
    
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDefaultStreetName() {
		return defaultStreetName;
	}
	public void setDefaultStreetName(String defaultStreetName) {
		this.defaultStreetName = defaultStreetName;
	}
	public Long getDefaultStreetNumber() {
		return defaultStreetNumber;
	}
	public void setDefaultStreetNumber(Long defaultStreetNumber) {
		this.defaultStreetNumber = defaultStreetNumber;
	}
	public Long getDefaultPincode() {
		return defaultPincode;
	}
	public void setDefaultPincode(Long defaultPincode) {
		this.defaultPincode = defaultPincode;
	}
	public String getDefaultCity() {
		return defaultCity;
	}
	public void setDefaultCity(String defaultCity) {
		this.defaultCity = defaultCity;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
    
    
}
