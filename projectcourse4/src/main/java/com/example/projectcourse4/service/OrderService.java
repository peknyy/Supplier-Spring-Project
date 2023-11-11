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
import org.springframework.stereotype.Service;

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

    @Autowired // constructor inject
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, JwtService jwtService, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.jwtService = jwtService;
        this.customerRepository = customerRepository;
    }

//    public Optional<Order> findByOrderName(String orderName) {
//        return orderRepository.findByOrderId(orderName);
//    }
    public Optional<Order> findByOrderId(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public void addProductInBox(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        order.getProducts().add(product);
        //orderRepository.save(order);
    }

    public void deleteProductInBox(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

//        if (order != null && product != null) {
           order.getProducts().remove(product);
//          product.getOrders().remove(order);

            //orderRepository.delete(order);

    }

    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order save(String authorizationHeader , OrderRequest orderRequest) {
        final String jwt;
        if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        jwt = authorizationHeader.substring(7);
        var order = Order.builder()
                .status(orderRequest.getStatus())
                .products(List.of())
                .orderDate(Date.valueOf(LocalDate.now()))
                .customer_id(customerRepository.findByCustomerName(jwtService.extractUsername(jwt)))
                .build();
        return orderRepository.save(order);
    }

    public Order updateStatus(Long orderId , OrderRequest orderRequest) {
//        final String jwt;
//        if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")) {
//            return null;
//        }
//        jwt = authorizationHeader.substring(7);
        var orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            var order = orderOpt.get();
            order.setStatus(orderRequest.getStatus());
            order = orderRepository.save(order);
            return order;
//            var order = Order.builder()
//                    .orderId(orderId)
//                    .status(orderRequest.getStatus())
//                    .products(orderRepository.findByOrderId(orderId).get().getProducts())
//                    .customer_id(customerRepository.findByCustomerName(jwtService.extractUsername(jwt)))
//                    .build();
//            return orderRepository.save(order);
        }
//        else return null;
        return null;

    }

//    public void delete(Customer customer) {
//        try {
//            if (customerRepository.existsBygroupName(customer.getCustomerName())) {
//                customerRepository.delete(customer);
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

}
