package org.fasttrackit.onlineshopapi.unitTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.persistence.ProductRepository;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceUnitTests {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    // aceasta adnotare va face ca aceasta metoda sa fie apelata inaintea fiecarei alte metode test
    // pentru a nu apela aceasta metoda in fiecare metoda in care avem nevoie
    @Before
    public void setup() {
        productService = new ProductService(productRepository, new ObjectMapper());
    }

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnProduct() {
        // cum sa se comporte repository cand primeste un call de la metoda .save

        Product product = new Product();
        product.setId(1);
        product.setName("test product");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        CreateProductRequest request = new CreateProductRequest();
        request.setName("test product2");
        request.setSku("fgfasd");

        Product saveProduct = productService.createProduct(request);

    // verifica daca clasa productRepository este apelata de cat re un obiect din clasa Product
        verify(productRepository).save(any(Product.class));

        assertThat(saveProduct, notNullValue());
        assertThat(saveProduct.getId(), is(product.getId()));


    }
}
