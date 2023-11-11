package com.example.projectcourse4.controller;

import com.example.projectcourse4.DTO.OrderRequest;
import com.example.projectcourse4.entity.Order;
import com.example.projectcourse4.service.OrderService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Iterable<Order> getAll () {
        return orderService.getAll();
    }

    @GetMapping("/findByOrderId/{orderId}")
    public ResponseEntity<Optional<Order>> findByOrderId (@PathVariable String orderId) {
        return new ResponseEntity<Optional<Order>>(orderService.findByOrderId(Long.valueOf(orderId)),HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteById(orderId);
    }

    @PostMapping("/saveOrder")
    @ResponseBody
    public Order addOrder(@RequestHeader("Authorization") String authorizationHeader,@RequestBody OrderRequest orderRequest) {
        return orderService.save(authorizationHeader,orderRequest);
    }

    @PutMapping("/addProductInOrder/{orderId}/{productId}")
    public Order addProductInOrder(@PathVariable("orderId") Long orderId,@PathVariable("productId") Long productId ,  @RequestBody OrderRequest orderRequest){
        orderService.addProductInBox(orderId, productId);
        return orderService.updateStatus(orderId , orderRequest);
    }

    @PutMapping("/deleteProductInOrder/{orderId}/{productId}")
    public Order deleteProductInOrder(@PathVariable("orderId") Long orderId,@PathVariable("productId") Long productId ,  @RequestBody OrderRequest orderRequest){
        orderService.deleteProductInBox(orderId, productId);
        return orderService.updateStatus(orderId , orderRequest);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

