package de.srh.toolify.frontend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class ProductForEdit extends Product {
    @JsonIgnore
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String quantity;
    private String manufacturer;
    private String voltage;
    private String productDimensions;
    private BigDecimal itemWeight;
    private String bodyMaterial;
    private String itemModelNumber;
    private String design;
    private String colour;
    private String batteriesRequired;
    private String image;
    private Long categoryTo;

    @JsonIgnore
    private Category category;

    @Override
    public Long getProductId() {
        return productId;
    }

    @Override
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public BigDecimal getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getBodyMaterial() {
        return bodyMaterial;
    }

    public void setBodyMaterial(String bodyMaterial) {
        this.bodyMaterial = bodyMaterial;
    }

    public String getItemModelNumber() {
        return itemModelNumber;
    }

    public void setItemModelNumber(String itemModelNumber) {
        this.itemModelNumber = itemModelNumber;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getBatteriesRequired() {
        return batteriesRequired;
    }

    public void setBatteriesRequired(String batteriesRequired) {
        this.batteriesRequired = batteriesRequired;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCategoryTo() {
        return categoryTo;
    }

    public void setCategoryTo(Long categoryTo) {
        this.categoryTo = categoryTo;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductForEdit{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", manufacturer='" + manufacturer + '\'' +
                ", voltage='" + voltage + '\'' +
                ", productDimensions='" + productDimensions + '\'' +
                ", itemWeight=" + itemWeight +
                ", bodyMaterial='" + bodyMaterial + '\'' +
                ", itemModelNumber='" + itemModelNumber + '\'' +
                ", design='" + design + '\'' +
                ", colour='" + colour + '\'' +
                ", batteriesRequired='" + batteriesRequired + '\'' +
                ", image='" + image + '\'' +
                ", categoryTo=" + categoryTo +
                '}';
    }
}
