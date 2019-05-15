package org.fasttrackit.onlineshopapi.persistence;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

// Long is wrapper class for primitive long
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

}
