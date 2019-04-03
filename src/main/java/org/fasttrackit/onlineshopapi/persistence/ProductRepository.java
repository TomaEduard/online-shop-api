package org.fasttrackit.onlineshopapi.persistence;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

// Long is wrapper class for primitive long
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}
