package org.fasttrackit.onlineshopapi.web;

import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.CartService;
import org.fasttrackit.onlineshopapi.transfer.cart.SaveCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity addProductsToCart(@RequestBody SaveCartRequest request) throws ResourceNotFoundException {
        cartService.addProductsToCart(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
