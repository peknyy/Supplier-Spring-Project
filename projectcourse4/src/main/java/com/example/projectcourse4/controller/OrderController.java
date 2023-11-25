package com.example.projectcourse4.controller;

import com.example.projectcourse4.DTO.OrderRequest;
import com.example.projectcourse4.entity.Order;
import com.example.projectcourse4.service.OrderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Iterable<Order> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/findByOrderId/{orderId}")
    public ResponseEntity<Optional<Order>> findByOrderId(@PathVariable String orderId) {
        return new ResponseEntity<Optional<Order>>(orderService.findByOrderId(Long.valueOf(orderId)), HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public HttpStatus deleteOrder(@PathVariable Long orderId) {
        orderService.deleteById(orderId);
        return HttpStatus.OK;
    }

    @PostMapping("/saveOrder")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public HttpStatus addOrder(@RequestHeader("Authorization") String authorizationHeader, @RequestBody OrderRequest orderRequest) {
        if (orderService.save(authorizationHeader, orderRequest) != null){
            return HttpStatus.CREATED;
        }
        return HttpStatus.NOT_FOUND;
    }

    @PutMapping("/addProductInOrder/{orderId}/{productId}")
    public HttpStatus addProductInOrder(@PathVariable("orderId") Long orderId, @PathVariable("productId") Long productId, @RequestBody OrderRequest orderRequest) {
        orderService.addProductInBox(orderId, productId);
        orderService.updateStatus(orderId, orderRequest);
        return HttpStatus.OK;
    }

    @DeleteMapping("/deleteProductInOrder/{orderId}/{productId}")
    public HttpStatus deleteProductInOrder(@PathVariable("orderId") Long orderId, @PathVariable("productId") Long productId, @RequestBody OrderRequest orderRequest) {
        orderService.deleteProductInBox(orderId, productId);
        return HttpStatus.OK;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}

