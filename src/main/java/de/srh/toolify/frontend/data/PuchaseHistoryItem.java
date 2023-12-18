package de.srh.toolify.frontend.data;

import java.math.BigDecimal;

public class PuchaseHistoryItem {
	private Long purchaseItemsId;
	private int quantity;
	private BigDecimal purchasePrice;
	private Product productEntity;
	
	public Long getPurchaseItemsId() {
		return purchaseItemsId;
	}
	public void setPurchaseItemsId(Long purchaseItemsId) {
		this.purchaseItemsId = purchaseItemsId;
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
	public Product getProductEntity() {
		return productEntity;
	}
	public void setProductEntity(Product productEntity) {
		this.productEntity = productEntity;
	}

}
