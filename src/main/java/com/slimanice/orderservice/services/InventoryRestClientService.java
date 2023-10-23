package com.slimanice.orderservice.services;

import com.slimanice.orderservice.model.Product;
import com.slimanice.orderservice.utils.FeignLogger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service", configuration = FeignLogger.class)
public interface InventoryRestClientService {
    @GetMapping("/products/{id}?projection=fullProduct")
    public Product productById(@PathVariable Long id);   // When triggered will request the customer-service
    @GetMapping("/products?projection=fullProduct")
    public PagedModel<Product> allProducts();
}
