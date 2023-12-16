package de.srh.toolify.frontend.data;

import java.math.BigDecimal;

public class PurchaseItem {
	
	private Long productId;
	private int quantity;
	private BigDecimal purchasePrice;
	private String productName;
	private int requestedQuantity;

	public PurchaseItem(Long productId, int quantity, BigDecimal purchasePrice, String productName, int requestedQuantity) {
		this.productId = productId;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.productName = productName;
		this.requestedQuantity = requestedQuantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(int requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}
	
}
