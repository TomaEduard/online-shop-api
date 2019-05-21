package org.fasttrackit.onlineshopapi.web;


import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ProductService;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController     // Folosim REST API sau RESTful API si nu unul SOAP
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Acestea adnotari(Autowired si Value) functioneaza in clase care au adnotarea:
    //  @RestController, @Service, @Repository, @Component
//    @Value("${server.port") // selectam priprietatea port din application.yml
//    private int port;       // stocam intr-o variabila proprietatea port de mai sus

    //    @RequestMapping(method = RequestMethod.GET, path = "/{id") Same as the @GetMapping annotation above
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) throws ResourceNotFoundException {
        Product response = productService.getProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Create
    // @RequestBody anunta springBoot ca asteptam un body content si nu un query params(din url)
    // @Valid va verifica conditile din DTO sa fiu indeplinite(@NotBlank) daca nu sunt
    // indeplinite va arunca o exceptie si nu va trece mai departe la serviciu
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest request) {
        Product response = productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") long id,
                                        @RequestBody @Valid UpdateProductRequest request) throws ResourceNotFoundException {
//        salvam intr-o variabila locala daca vrem sa returnam produsul
        productService.updateProduct(id, request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id){
//        salvam intr-o variabila locala daca vrem sa returnam produsul
        productService.deleteProduct(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // @RequestParam(request = true, name = "userId")30.20

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(@Valid GetProductsRequest request, Pageable pageable) {
        Page<Product> response = productService.getProducts(request, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
