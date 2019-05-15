package org.fasttrackit.onlineshopapi.persistence;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

// Long is wrapper class for primitive long
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findByNameContaining(String partialName, Pageable pageable);

    //    Same result as the method above
//    @Query(value = "SELECT id, name, price, quantity, sku FROM product WHERE name LIKE '%?1'", nativeQuery = true)
    @Query(value = "SELECT id, name, price, quantity, sku FROM Product product WHERE name LIKE '%?1'")
    Page<Product> findByPartialName(String partialName, Pageable pageable);

    // find by name
    Page<Product> findByNameContainingAndQuantityIsGreaterThanEqual(
            String partialName, int minimumQuantity, Pageable pageable);

    // find by price
    Page<Product> findByPriceBetweenAndQuantityIsGreaterThanEqual(
            double minimumPrice, double maximumPrice, int minimumQuantity, Pageable pageable);

    // find by name and price
    Page<Product> findByNameContainingAndPriceBetweenAndQuantityGreaterThanEqual(
         String partialName, double minimumPrice, double maximumPrice, int minimumQuantity, Pageable pageable);

}
