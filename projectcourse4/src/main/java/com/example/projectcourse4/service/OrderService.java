package com.example.projectcourse4.service;

import com.example.projectcourse4.DTO.OrderRequest;
import com.example.projectcourse4.configuration.JwtService;
import com.example.projectcourse4.entity.Order;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.repository.CustomerRepository;
import com.example.projectcourse4.repository.OrderRepository;
import com.example.projectcourse4.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, JwtService jwtService, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.jwtService = jwtService;
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Order> findByOrderId(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public void addProductInBox(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        order.getProducts().add(product);
    }

    @Transactional
    public void deleteProductInBox(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        order.getProducts().remove(product);
    }

    @Transactional
    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order save(String authorizationHeader, OrderRequest orderRequest) {
        try {
            final String jwt;
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                logger.error("Invalid authorization header");
            }
            jwt = authorizationHeader.substring(7);
            var order = Order.builder()
                    .status(orderRequest.getStatus())
                    .products(List.of())
                    .orderDate(Date.valueOf(LocalDate.now()))
                    .customer_id(customerRepository.findByCustomerName(jwtService.extractUsername(jwt)))
                    .build();
            return orderRepository.save(order);
        } catch (Exception e) {
            logger.error("Error saving order: {}", e.getMessage());
            return null;
        }
    }

    @Transactional
    public Order updateStatus(Long orderId, OrderRequest orderRequest) {
        try {
            var orderOpt = orderRepository.findById(orderId);
            if (orderOpt.isPresent()) {
                var order = orderOpt.get();
                order.setStatus(orderRequest.getStatus());
                order = orderRepository.save(order);
                return order;
            } else {
                throw new RuntimeException("Order not found");
            }
        } catch (Exception e) {
            logger.error("Error updating order status: {}", e.getMessage());
            return null;
        }
    }

    public void deleteById(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting order: {}", e.getMessage());

        }
    }
}
