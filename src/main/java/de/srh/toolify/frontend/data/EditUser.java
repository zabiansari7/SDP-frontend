package de.srh.toolify.frontend.data;

public class EditUser {
	private String firstname;
    private String lastname;
    private String mobile;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	
}
