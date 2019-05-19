package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.steps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// Aceasta clasa de teste se ruleaza cu ajutorul clasei SpringRunner.class
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    // Am injectat dependinta serviciului(dependency injection)
    // Acest inversion of control(IoC) spring boot se ocupa de el, el face instantierea
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSteps productSteps;


    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProductWithId() {
        Product product = productSteps.createProduct();

        // Make sure the product does not have null values
        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));
    }

//    Get product
    @Test
    public void testGetProduct_whenExistingId_thenReturnMatchingProduct() throws ResourceNotFoundException {
        Product product = productSteps.createProduct();

        Product retrievedProduct = productService.getProduct(product.getId());
        assertThat(retrievedProduct.getId(), is(product.getId()));
        assertThat(retrievedProduct.getName(), is(product.getName()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_whenProductNotFound_ThrowException() throws ResourceNotFoundException {
        // Ne asiguram ca obiectul nu exista in baza. In baza obiectele incep de la id'ul 1
        productService.getProduct(0);
    }

//    Update Product
    @Test
    public void testUpdateProduct_whenValidRequestWithAllFields_thenReturnUpdatedProduct() throws ResourceNotFoundException {

        // Simulam obiectul in db
        Product createdProduct = productSteps.createProduct();

        // Simulam requestul
        UpdateProductRequest request = new UpdateProductRequest();
        request.setName(productSteps.createProduct().getName()+ "_Edited");
        request.setPrice(productSteps.createProduct().getPrice()+ 10);
        request.setQuantity(productSteps.createProduct().getQuantity() + 10);
        request.setSku(productSteps.createProduct().getSku()+"_Edited");

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);
        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getName(), not(is(createdProduct.getName())));

        // Ne asiguram ca datele existente in baza se modifica cu datele primite de la request
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getSku(), is(request.getSku()));

        // Ne asiguram ca am updatat acelasi produs
        assertThat(updatedProduct.getId(), is(createdProduct.getId()));
    }

//    todo: Implement negative tests for update and tests for update with some filds only

    @Test (expected = ResourceNotFoundException.class)
    public void testDeleteProduct_whenEdistingId_thenProductIsDeleted() throws ResourceNotFoundException {
        Product createdProduct = productSteps.createProduct();

        productService.deleteProduct(createdProduct.getId());

        productService.getProduct(createdProduct.getId());
    }

    @Test
    public void testGetProducts_whenAllCriteriaProvidedAndMatching_thenReturnFilteredResults(){
        Product createdProduct = productSteps.createProduct();

        GetProductsRequest request = new GetProductsRequest();
        request.setPartialName("top");
        request.setMinimumPrice(9.9);
        request.setMaximumPrice(10.1);
        request.setMinimumQuantity(1);

        Page<Product> products = productService.getProducts(request, PageRequest.of(0, 10));

        assertThat(products.getTotalElements(), greaterThanOrEqualTo(1L));

        // Parcurgem cu un for toate produsele create si pentru fiecare sa facem mai multe asserturi
        // contine top , etc. Pentru fiecare criteriu creat
        // TODO: for each product from the response assert that all criteria ar matched
    }
}
