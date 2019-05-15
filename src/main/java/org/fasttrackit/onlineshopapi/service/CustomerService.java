package org.fasttrackit.onlineshopapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.CustomerRepository;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.UpdateCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.util.logging.Logger;

@Service
public class CustomerService {

//    Avem o clasa LoggerFactory oferita de spring care printeaza mesajele in consola
//    Daca este static final va fo un singur LOGGER in toata aplicatia
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CustomerService.class);
//    Tinem evidenta pasilor realizati prin .info si pentru informatii mai detaliate .debug

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    public Customer createCustomer(CreateCustomerRequest request) {
//        System.out.println("Creating customer with sout if not exist print null " + request );
        LOGGER.info("Creating customer {}", request );
        Customer customer = objectMapper.convertValue(request, Customer.class);
        return customerRepository.save(customer);
    }

    public Customer getCustomer(long id) throws ResourceNotFoundException {
        LOGGER.info("Retriving customer {}", id );
//        Returnam produsul cu idulul respectiv, daca nu s-a gasit atunci aruncam exceptia
        return customerRepository.findById(id)
//                Optional and lambda expression
                .orElseThrow(() -> new ResourceNotFoundException("Customer" + id + "Resource not found"));

/*
//        sau modelul vechi
        Optional<Customer> optional = customerRepository.findById(id);
//        daca optional exista returnam continutul daca nu returnam exceptia
        if (optional.isPresent() ) {
            return optional.get();
        } else {
            throw new Exception("Customer" + id + "Resource not found");
        }
*/
    }

    public Customer updateCustomer(long id, UpdateCustomerRequest request) throws ResourceNotFoundException {
        LOGGER.info("Updating customer {}, {}", id, request);
//        Aducem produsul din db cu id'ul primit
        Customer customer = getCustomer(id);

//        Copiam proprietatile ce le primim pe request si le copiam in procuct
//        Daca nu folosim BeanUtils va trebui pt fiecare proprietate: customer.setName(request.getName());
        BeanUtils.copyProperties(request, customer);

//        Metoda .save returneaza produsul updatat. Daca promitem in semnatura metodei return, mai bine
        return customerRepository.save(customer);
    }

    public void deleteCustomer(long id) {
        LOGGER.info("Deleting customer {}",id);

        customerRepository.deleteById(id);
        LOGGER.info("Deleted customer {}",id);
    }


}
