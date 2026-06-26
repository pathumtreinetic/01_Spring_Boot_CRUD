package com.example.__learn.dto;

public class AddItemToCartRequest {
    private String productId;

    public AddItemToCartRequest() {
    }

    public AddItemToCartRequest(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
