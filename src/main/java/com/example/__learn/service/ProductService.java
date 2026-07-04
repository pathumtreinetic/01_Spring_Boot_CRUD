package com.example.__learn.service;

import com.example.__learn.Entity.Products;
import com.example.__learn.Entity.Users;
import com.example.__learn.dto.ApiResponse;
import com.example.__learn.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public boolean productNameExist(Products product){
        if(productRepo.existsByProductName(product.getProductName())){
            return true;
        }
        return false;
    }

    public ApiResponse add(Products product) {
        Products productsRes = productRepo.save(product);
        return new ApiResponse<>("Successfully add Product.", productsRes);
    }

    public List<Products> getAll() {
        List<Products> products = productRepo.findAll();
        return products;
    }

    public List<Products> filterByName(String name) {
        return productRepo.findByProductNameContainingIgnoreCase(name);
    }

    public List<Products> filterByPrice(Double minPrice, Double maxPrice) {

        List<Products> allProducts = productRepo.findAll();

        return allProducts.stream()
                .filter(p -> {
                    System.out.println("Price: " + p.getUnitPrice());
                    System.out.println("Min: " + minPrice + " Max: " + maxPrice);

                    boolean minOk = (minPrice == null) || p.getUnitPrice() >= minPrice;
                    boolean maxOk = (maxPrice == null) || p.getUnitPrice() <= maxPrice;
                    return minOk && maxOk;
                })
                .toList();
    }

}
