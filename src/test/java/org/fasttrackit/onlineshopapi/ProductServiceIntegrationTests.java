package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.UpdateProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// Aceasta clasa de teste se ruleaza cu ajutorul clasei SpringRunner.class
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

//    Am injectat dependinta serviciului(dependency injection)
//    Acest inversion of control(IoC) spring boot se ocupa de el, el face instantierea
    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProductWithId() {
        Product product = createProduct();

        // Make sure the product does not have null values
        assertThat(product, notNullValue());
        assertThat(product.getId(), greaterThan(0L));
    }

    private Product createProduct() {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(10);
        request.setQuantity(3);
        request.setSku("numarUnic");

        return productService.createProduct(request);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_whenProductNotFound_ThrowException() throws ResourceNotFoundException {
//        Ne asiguram ca obiectul nu exista in baza. In baza obiectele incep de la id'ul 1
        productService.getProduct(0);
    }

    @Test
    public void testGetProduct_whenExistingId_thenReturnMatchingProduct() throws ResourceNotFoundException {
        Product product = createProduct();

        Product retrievedProduct = productService.getProduct(product.getId());
        assertThat(retrievedProduct.getId(), is(product.getId()));
        assertThat(retrievedProduct.getName(), is(product.getName()));
    }

    @Test
    public void testUpdateProduct_whenValidRequestWithAllFields_thanReturnUpdatedProduct() throws ResourceNotFoundException {
//        Simulam obiectul din db
        Product createdProduct = createProduct();

//        Simulam requestul
        UpdateProductRequest request = new UpdateProductRequest();
        request.setName(createProduct().getName()+ "_Edited");
        request.setPrice(createProduct().getPrice()+ 10);
        request.setQuantity(createProduct().getQuantity() + 10);
        request.setSku(createProduct().getSku()+"_Edited");

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);
        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getName(), not(is(createdProduct.getName())));

//        Ne asiguram ca datele existente in baza se modifica cu datele primite de la request
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));
        assertThat(updatedProduct.getSku(), is(request.getSku()));

//        Ne asiguram ca am updatat acelasi produs
        assertThat(updatedProduct.getId(), is(createdProduct.getId()));
    }

//    todo: Implement negative tests for update and tests for update with some filds only

    @Test (expected = ResourceNotFoundException.class)
    public void testDeleteProduct_whenEdistingId_thanProductIsDeleted() throws ResourceNotFoundException {
        Product createdProduct = createProduct();

        productService.deleteProduct(createdProduct.getId());

        productService.getProduct(createdProduct.getId());

    }

}
