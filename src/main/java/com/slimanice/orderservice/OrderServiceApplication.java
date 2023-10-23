package com.slimanice.orderservice;

import com.slimanice.orderservice.entities.Order;
import com.slimanice.orderservice.entities.ProductItem;
import com.slimanice.orderservice.enums.OrderStatus;
import com.slimanice.orderservice.model.Customer;
import com.slimanice.orderservice.model.Product;
import com.slimanice.orderservice.repository.OrderRepository;
import com.slimanice.orderservice.repository.ProductItemRepository;
import com.slimanice.orderservice.services.CustomerRestClientService;
import com.slimanice.orderservice.services.InventoryRestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Versioned;

import java.util.*;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

	@Autowired
	private VaultTemplate vaultTemplate;

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(
			OrderRepository orderRepository,
			ProductItemRepository productItemRepository,
			CustomerRestClientService customerRestClientService,
			InventoryRestClientService inventoryRestClientService) {
		return args -> {
			List<Customer> customers = customerRestClientService.allCustomers().getContent().stream().toList();
			List<Product> products = inventoryRestClientService.allProducts().getContent().stream().toList();
			List<OrderStatus> orderStatuses = List.of(OrderStatus.CREATED, OrderStatus.CANCELED, OrderStatus.PENDING, OrderStatus.DELIVERED);
			Random rnd = new Random();
			for (int i = 0; i < 20; i++) {
				Order order = Order.builder()
						.customerId(customers.get(rnd.nextInt(customers.size())).getId())
						.createdAt(new Date())
						.status(orderStatuses.get(rnd.nextInt(orderStatuses.size())))
						.build();
				Order savedOrder = orderRepository.save(order);
				for (int j = 0; j < products.size(); j++) {
					if (Math.random() > 0.7) {
						ProductItem productItem = ProductItem.builder()
								.order(savedOrder)
								.productId(products.get(j).getId())
								.price(products.get(j).getPrice())
								.quantity(rnd.nextInt(10) + 1)
								.discount(Math.random())
								.build();
						productItemRepository.save(productItem);
					}
				}
			}
			Versioned.Metadata secret = vaultTemplate.opsForVersionedKeyValue("secret")
					.put("keypair", Map.of("prvKey", "654321", "publKey", 123456));
		};
	}
}
