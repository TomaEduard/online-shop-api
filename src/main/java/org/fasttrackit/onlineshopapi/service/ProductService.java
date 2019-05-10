package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.ProductRepository;
import org.fasttrackit.onlineshopapi.transfer.CreateProductRequest;
//import java.util.logging.Logger;
import org.fasttrackit.onlineshopapi.transfer.UpdateProductRequest;
import org.slf4j.Logger; // sa fie la fel ca cel de la curs
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductService {

//    Avem o clasa LoggerFactory oferita de spring care printeaza mesajele in consola
//    Daca este static final va fo un singur LOGGER in toata aplicatia
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ProductService.class);
//    Tinem evidenta pasilor realizati prin .info si pentru informatii mai detaliate .debug

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public Product createProduct(CreateProductRequest request) {
        System.out.println("Creating product with sout if not exist print null " + request );
        LOGGER.info("Creating product {}", request );
        Product product = objectMapper.convertValue(request, Product.class);
        return productRepository.save(product);
    }

    public Product getProduct(long id) throws ResourceNotFoundException {
        LOGGER.info("Retriving product {}", id );
//        Returnam produsul cu idulul respectiv, daca nu s-a gasit atunci aruncam exceptia
        return productRepository.findById(id)
//                Optional and lambda expression
                .orElseThrow(() -> new ResourceNotFoundException("Product" + id + "Resource not found"));

/*
//        sau modelul vechi
        Optional<Product> optional = productRepository.findById(id);
//        daca optional exista returnam continutul daca nu returnam exceptia
        if (optional.isPresent() ) {
            return optional.get();
        } else {
            throw new Exception("Product" + id + "Resource not found");
        }
*/
    }

    public Product updateProduct(long id, UpdateProductRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating product {}, {}", id, request);
//        Aducem produsul din db cu id'ul primit
        Product product = getProduct(id);

//        Copiam proprietatile ce le primim pe request si le copiam in procuct
//        Daca nu folosim BeanUtils va trebui pt fiecare proprietate: product.setName(request.getName());
        BeanUtils.copyProperties(request, product);

//        Metoda .save returneaza produsul updatat. Daca promitem in semnatura metodei return, mai bine
        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product {}",id);

        productRepository.deleteById(id);
        LOGGER.info("Deleted product {}",id);
    }


}
