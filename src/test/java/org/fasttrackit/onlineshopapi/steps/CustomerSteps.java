package org.fasttrackit.onlineshopapi.steps;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component      // ia acest obiect si-l pune in containerul de beanuri
public class CustomerSteps {

    @Autowired
    private CustomerService customerService;

    public Customer createCustomer() {
        CreateCustomerRequest customer = new CreateCustomerRequest();
        customer.setFirstName("Jhon");
        customer.setLastName("Doe");
        customer.setAddress("test address");
        customer.setEmail("Jhon@exemple.com");
        customer.setPhone("0423313");
        customer.setUsername("jhonny");
        customer.setPassword("pass");

        return customerService.createCustomer(customer);
    }

}
