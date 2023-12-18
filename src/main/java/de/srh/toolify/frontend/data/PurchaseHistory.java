package de.srh.toolify.frontend.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class PurchaseHistory {
	
	private Long purchaseId;
	private User user;
	private Instant date;
	private BigDecimal totalPrice;
	private int invoice;
	private Address address;
	private List<PuchaseHistoryItem> purchaseItemsEntities;
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Instant getDate() {
		return date;
	}
	public void setDate(Instant date) {
		this.date = date;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getInvoice() {
		return invoice;
	}
	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<PuchaseHistoryItem> getPurchaseItemsEntities() {
		return purchaseItemsEntities;
	}
	public void setPurchaseItemsEntities(List<PuchaseHistoryItem> purchaseItemsEntities) {
		this.purchaseItemsEntities = purchaseItemsEntities;
	}

}
