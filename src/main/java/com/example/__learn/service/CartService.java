package com.example.__learn.service;

import com.example.__learn.Entity.Carts;
import com.example.__learn.Entity.Products;
import com.example.__learn.dto.AddItemToCartRequest;
import com.example.__learn.dto.ApiResponse;
import com.example.__learn.dto.CartItemDto;
import com.example.__learn.exception.CartNotFoundException;
import com.example.__learn.exception.ProductNotFoundException;
import com.example.__learn.repository.CartRepo;
import com.example.__learn.repository.ProductRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private CartRepo cartRepo;
    private ProductRepo productRepo;

    public CartService(CartRepo cartRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    public ApiResponse<Carts> newCart(String id) {
        Carts newCart = new Carts(id);
        Carts cart = cartRepo.save(newCart);
        return new ApiResponse<Carts>("Success created cart.",cart);
    }

    public ApiResponse<CartItemDto> addProductToCart(String cartId, AddItemToCartRequest cartRequest) {
        Carts findCart = cartRepo.findById(cartId)
                .orElseThrow(()-> new CartNotFoundException("This Cart cartId:"+ cartId+" not found."));

        Products product = productRepo.findById(cartRequest.getProductId())
                .orElseThrow(()->new ProductNotFoundException("This product id:"+cartRequest.getProductId()+" not found."));

        List<CartItemDto> productsListFromCart =  findCart.getItems(); //load cart items

        long count = productsListFromCart.stream()
                .filter(p -> p.getProductId().equals(product.getProductId()))
                .count();

        if(count>0){
            CartItemDto matchedItem = productsListFromCart.stream()
                    .filter(p -> p.getProductId().equals(product.getProductId()))
                    .findFirst()
                    .orElse(null);

            System.out.println("matchedItem: "+matchedItem);

            matchedItem.setQuantity(matchedItem.getQuantity() + 1);
            matchedItem.setTotalPrice(
                    matchedItem.getTotalPrice().add(matchedItem.getUnitPrice())
            );

            findCart.setItems(productsListFromCart);
            cartRepo.save(findCart);

            setCartTotalPrice(cartId); //store total cart price
            return new ApiResponse<CartItemDto>("product add success. ", matchedItem );
        } else{
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setProductId(product.getProductId());
            cartItemDto.setProductName(product.getProductName());
            cartItemDto.setUnitPrice(BigDecimal.valueOf(product.getUnitPrice()));
            cartItemDto.setQuantity(1);
            cartItemDto.setTotalPrice(BigDecimal.valueOf(product.getUnitPrice()));

            productsListFromCart.add(cartItemDto);
            findCart.setItems(productsListFromCart);
            cartRepo.save(findCart);

            setCartTotalPrice(cartId); //store total cart price
            return new ApiResponse<CartItemDto>("product add success. ", cartItemDto );
        }

    }

    public void setCartTotalPrice(String cartId) {

        Carts cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        BigDecimal total = cart.getItems().stream()
                .map(CartItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(total);

        cartRepo.save(cart);
    }

    public ApiResponse<Carts> getCartById(String cartId) {
        Carts findCart = cartRepo.findById(cartId)
                .orElseThrow(()-> new CartNotFoundException("This Cart not found."));

        return new ApiResponse<Carts>("Cart Find success.", findCart);
    }

    public ApiResponse<CartItemDto> updateQuantity(String cartId, String productId, Integer quantity) {

        // Find cart
        Carts cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        // Find item in cart
        CartItemDto item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found in cart"));

        // Update quantity
        item.setQuantity(quantity);

        // Recalculate item total price
        item.setTotalPrice(
                item.getUnitPrice().multiply(BigDecimal.valueOf(quantity))
        );

        // Recalculate cart total price
        BigDecimal cartTotal = BigDecimal.ZERO;

        for (CartItemDto i : cart.getItems()) {
            if (i.getTotalPrice() != null) {
                cartTotal = cartTotal.add(i.getTotalPrice());
            }
        }

        cart.setTotalPrice(cartTotal);

        cartRepo.save(cart);
        return new ApiResponse<>("Updated Cart Item.", null);
    }

    public ApiResponse<CartItemDto> deleteProduct(String cartId, String productId) {

        // Find cart
        Carts cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        // Find item to remove
        CartItemDto itemToRemove = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found in cart"));

        // Remove item
        cart.getItems().remove(itemToRemove);

        // Recalculate cart total
        BigDecimal cartTotal = BigDecimal.ZERO;
        for (CartItemDto item : cart.getItems()) {
            if (item.getTotalPrice() != null) {
                cartTotal = cartTotal.add(item.getTotalPrice());
            }
        }
        cart.setTotalPrice(cartTotal);

        // Save cart
        cartRepo.save(cart);

        // Return response
        return new ApiResponse<>("Product removed successfully", null);
    }

    public ApiResponse clearCart(String cartId) {
        // Find cart
        Carts cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));


        cart.setItems(new ArrayList<CartItemDto>());

        // Recalculate cart total
        BigDecimal cartTotal = BigDecimal.ZERO;
        cart.setTotalPrice(cartTotal);

        // Save cart
        cartRepo.save(cart);

        // Return response
        return new ApiResponse<>("Cart clear successfully", null);
    }
}
