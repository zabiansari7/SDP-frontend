package de.srh.toolify.frontend.data;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	@JsonIgnore
	private Long userId;
	private String firstname;
    private String lastname;
    private String email;
    private String mobile;
    private String password;
    @JsonIgnore
    private String repeatPassword;
    @JsonIgnore
    private String hasRole;
    private String defaultStreetName;
    private Long defaultStreetNumber;
    private Long defaultPincode;
    private String defaultCity;
    @JsonIgnore
    private Instant createdOn;
    @JsonIgnore
    private Instant updatedOn;
    
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
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
	
	
	public String getHasRole() {
		return hasRole;
	}
	
	public void setHasRole(String hasRole) {
		this.hasRole = hasRole;
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

	@Override
	public String toString() {
		return "User [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", mobile=" + mobile
				+ ", defaultStreetName=" + defaultStreetName + ", defaultStreetNumber=" + defaultStreetNumber
				+ ", defaultPincode=" + defaultPincode + ", defaultCity=" + defaultCity + "]";
	}
	
	
	
}
