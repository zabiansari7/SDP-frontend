package de.srh.toolify.frontend.data;

import java.util.List;

public class CheckoutRequest {
	
	private String email;
	private Long addressId;
	private List<CheckoutPurchaseItem> purchaseItems;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public List<CheckoutPurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}
	
	public void setPurchaseItems(List<CheckoutPurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

}
