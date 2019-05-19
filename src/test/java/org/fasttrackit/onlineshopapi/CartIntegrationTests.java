package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Cart;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.CartService;
import org.fasttrackit.onlineshopapi.steps.CustomerSteps;
import org.fasttrackit.onlineshopapi.steps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.cart.SaveCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartIntegrationTests {

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductSteps productSteps;

	@Autowired
	private CustomerSteps customerSteps;

	@Test
	public void testAddProductsToCart_whenValidRequest_thanReturnCart() throws ResourceNotFoundException {
		// simulam obiectele in db
		Product product = productSteps.createProduct();
		Customer customer = customerSteps.createCustomer();

		// simulam requesturile
		SaveCartRequest request = new SaveCartRequest();
		request.setCustomerId(customer.getId());
		request.setProductIds(Collections.singleton(product.getId())); // returneaza 1 set cu 1singur element

		Cart cart = cartService.addProductsToCart(request);
		assertThat(cart, notNullValue());
		assertThat(cart.getId(), is(customer.getId()));
		assertThat(cart.getProducts(), hasSize(1));

	}

}
