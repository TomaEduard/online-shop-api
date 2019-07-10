package org.fasttrackit.onlineshopapi.service;

import org.fasttrackit.onlineshopapi.domain.Cart;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.CartRepository;
import org.fasttrackit.onlineshopapi.transfer.cart.SaveCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;    // dependinta catre repository-db
    private final CustomerService customerService;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public Cart addProductsToCart(SaveCartRequest request) throws ResourceNotFoundException {
        LOGGER.info("Adding products to cart: {}", request);

        Customer customer = customerService.getCustomer(request.getCustomerId()); // obtinem customerul din db

        Cart cart = new Cart(); // cream un obiect cart
        cart.setCustomer(customer); // adaugam cartului, customerul cu acelasi id

        for (Long id : request.getProductIds()) {
            // could be done more efficiently with a getAllProductsByIds
            Product product = productService.getProduct(id);
            cart.addProduct(product);
        }
        return cartRepository.save(cart);
    }

}
