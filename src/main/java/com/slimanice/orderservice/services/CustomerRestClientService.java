package com.slimanice.orderservice.services;

import com.slimanice.orderservice.model.Customer;
import com.slimanice.orderservice.utils.FeignLogger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "customer-service", configuration = FeignLogger.class)
public interface CustomerRestClientService {
    @GetMapping("/customers/{id}?projection=fullCustomer")
    public Customer customerById(@PathVariable Long id);   // When triggered will request the customer-service
    @GetMapping("/customers?projection=fullCustomer")
    public PagedModel<Customer> allCustomers();
}
