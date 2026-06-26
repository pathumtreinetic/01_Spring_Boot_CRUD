package com.example.__learn.Entity;

import com.example.__learn.dto.CartItemDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("carts")
public class Carts {
    @Id
    private String id;
    private LocalDateTime createdAt;
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
    private String userId;

    public Carts() {
        this.createdAt = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
    }

    public Carts(String userId) {
        this();
        this.userId = userId;
    }

    public Carts(String id, LocalDateTime createdAt, List<CartItemDto> items, BigDecimal totalPrice, String userId) {
        this.id = id;
        this.createdAt = createdAt;
        this.items = items;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
