package com.slimanice.orderservice.model;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String name;
    private String email;
}
