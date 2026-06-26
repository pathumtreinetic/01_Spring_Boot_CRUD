package com.example.__learn.controller;

import com.example.__learn.Entity.Carts;
import com.example.__learn.dto.AddItemToCartRequest;
import com.example.__learn.dto.ApiResponse;
import com.example.__learn.dto.CartItemDto;
import com.example.__learn.dto.UpdateCartItemRequest;
import com.example.__learn.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse<CartItemDto>> addProductToCart(@PathVariable String cartId, @RequestBody AddItemToCartRequest cartRequest){
        ApiResponse<CartItemDto> res = cartService.addProductToCart(cartId, cartRequest);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Carts>> getCartById(@PathVariable String cartId){
        ApiResponse<Carts> res = cartService.getCartById(cartId);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<ApiResponse<CartItemDto>> updateQuantity(
            @PathVariable String cartId,
            @PathVariable String productId,
            @RequestBody @jakarta.validation.Valid UpdateCartItemRequest req) {

        ApiResponse<CartItemDto> response =
                cartService.updateQuantity(cartId, productId, req.getQuantity());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String cartId, @PathVariable String productId){
        ApiResponse response =
                cartService.deleteProduct(cartId, productId);

        return ResponseEntity.status(204).body(response);
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String cartId){
        ApiResponse response = cartService.clearCart(cartId);

        return ResponseEntity.status(204).body(response);
    }
}
