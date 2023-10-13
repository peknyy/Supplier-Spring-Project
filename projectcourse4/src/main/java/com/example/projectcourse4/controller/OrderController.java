package com.example.projectcourse4.controller;

import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.entity.Order;
import com.example.projectcourse4.service.CustomerService;
import com.example.projectcourse4.service.OrderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Iterable<Order> getAll () {
        return orderService.getAll();
    }

    @GetMapping("/findByOrderName/{orderId}")
    public ResponseEntity<Optional<Order>> findByOrderName (@PathVariable String orderId) {
        return new ResponseEntity<Optional<Order>>(orderService.findByOrderId(Long.valueOf(orderId)),HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder")
    public void deleteOrder(Long orderId) {
        orderService.deleteById(orderId);
    }

    @PostMapping("/saveOrder")
    @ResponseBody
    public Order addOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @GetMapping("/updateOrder")
    public Order updateOrder(@RequestBody Order order){
        return orderService.update(order);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

