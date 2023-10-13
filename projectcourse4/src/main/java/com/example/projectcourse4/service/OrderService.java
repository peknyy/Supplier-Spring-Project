package com.example.projectcourse4.service;

import com.example.projectcourse4.entity.Order;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.repository.OrderRepository;
import com.example.projectcourse4.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    @Autowired // constructor inject
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

//    public Optional<Order> findByOrderName(String orderName) {
//        return orderRepository.findByOrderId(orderName);
//    }
    public Optional<Order> findByOrderId(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public Iterable<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Order order) {
        return orderRepository.save(order);
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
