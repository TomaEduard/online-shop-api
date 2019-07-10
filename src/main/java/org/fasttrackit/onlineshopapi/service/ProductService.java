package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.ProductRepository;
import org.fasttrackit.onlineshopapi.transfer.product.CreateProductRequest;
import org.fasttrackit.onlineshopapi.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshopapi.transfer.product.ProductResponse;
import org.fasttrackit.onlineshopapi.transfer.product.UpdateProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import java.util.logging.Logger;

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
//        System.out.println("Creating product with sout if not exist print null " + request );
        LOGGER.info("Creating product {}", request);
        Product product = objectMapper.convertValue(request, Product.class);
        return productRepository.save(product);
    }

    //    Get & Update Read
    public Product getProduct(long id) throws ResourceNotFoundException {
        LOGGER.info("Retriving product {}", id);
//        Returnam produsul cu idulul respectiv, daca nu s-a gasit atunci aruncam exceptia
        return productRepository.findById(id)
//                Optional and lambda expression
                .orElseThrow(() -> new ResourceNotFoundException("Product" + id + "Resource not found"));

////        sau modelul vechi
//        Optional<Product> optional = productRepository.findById(id);
////        daca optional exista returnam continutul daca nu returnam exceptia
//        if (optional.isPresent() ) {
//            return optional.get();
//        } else {
//            throw new Exception("Product" + id + "Resource not found");
//        }
    }

    public Page<ProductResponse> getProducts(GetProductsRequest request, Pageable pageable) {
        LOGGER.info("Retriving product >> {} ", request);
//      Objects.nonNull(request.getMaximumPrice() = request.getMaximumPrice() != null

        Page<Product> products;

//       find dupa nume & pret
        if (request.getPartialName() != null &&
                request.getMinimumPrice() != null &&
                request.getMaximumPrice() != null &&
                request.getMinimumQuantity() != null) {
            products = productRepository.findByNameContainingAndPriceBetweenAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinimumPrice(),
                    request.getMaximumPrice(), request.getMinimumQuantity(), pageable);

//       find dupa pret
        } else if (request.getMinimumPrice() != null &&
                request.getMaximumPrice() != null &&
                request.getMinimumQuantity() != null) {
            products = productRepository.findByPriceBetweenAndQuantityIsGreaterThanEqual(
                        request.getMinimumPrice(), request.getMaximumPrice(),
                        request.getMinimumQuantity(),pageable);

//       find dupa nume
        } else if (request.getPartialName() != null &&
                request.getMinimumQuantity() != null) {
            products = productRepository.findByNameContainingAndQuantityIsGreaterThanEqual(
                    request.getPartialName(), request.getMinimumQuantity(), pageable);
        } else {
            products = productRepository.findAll(pageable);
        }

//        pentru fiecare obiect din db obtinem cate un obiect ProductResponse pe care il adaugam intr-o lista
        List<ProductResponse> productResponseList = new ArrayList<>();
        for(Product product : products.getContent()) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());
            productResponse.setQuantity(product.getQuantity());
            productResponse.setSku(product.getSku());

            productResponseList.add(productResponse);
        }
        return new PageImpl<>(productResponseList, pageable, products.getTotalElements());
    }

    public Product updateProduct(long id, UpdateProductRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating product {}, {}", id, request);
//        Aducem produsul din db cu id'ul primit
        Product product = getProduct(id);

//        Copiam proprietatile ce le primim pe request si le copiam in product
//        Daca nu folosim BeanUtils va trebui pt fiecare proprietate: product.setName(request.getName());
        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);
}

    //    Delete
    public void deleteProduct(long id) {
        LOGGER.info("Deleting movie {}", id);

        productRepository.deleteById(id);
        LOGGER.info("Deleted movie {}", id);
    }


}
