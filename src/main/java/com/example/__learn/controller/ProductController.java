package com.example.__learn.controller;

import com.example.__learn.Entity.Products;
import com.example.__learn.Entity.Users;
import com.example.__learn.dto.ApiResponse;
import com.example.__learn.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody Products product){

        try {
            if(productService.productNameExist(product)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>("This Product Name Already Exist.", null));
            }

            ApiResponse res =  productService.add(product);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> all(){
        List<Products> productList = productService.getAll();
        if(productList.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Empty products list.", null));

        return ResponseEntity.ok(new ApiResponse<>("Products Fetched Success.", productList));
    }
}
