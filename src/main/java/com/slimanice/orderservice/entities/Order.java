package com.slimanice.orderservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slimanice.orderservice.enums.OrderStatus;
import com.slimanice.orderservice.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity @Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Table(name="orders") // Explicitly name the table so we can prevent reserved names
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private OrderStatus status;
    private Long customerId;
    @Transient
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<ProductItem> productItemList;

    public double getTotal() {
        double sum = 0;
        for(ProductItem p : productItemList)
            sum += p.getSoldPrice();
        return sum;
    }
}
