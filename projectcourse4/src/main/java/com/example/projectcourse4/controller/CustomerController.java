package com.example.projectcourse4.controller;

import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.entity.User;
import com.example.projectcourse4.service.CustomerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @GetMapping
//    public Iterable<Customer> getAll () {
//        return customerService.getAll();
//    }

    @GetMapping("/findByCustomerName/{customerName}")
    public ResponseEntity<Optional<Customer>> findByCustomerName (@PathVariable String customerName) {
        return new ResponseEntity<Optional<Customer>>(customerService.findByCustomerName(customerName), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer")
    public void deleteCustomer(Long customerId) {
        customerService.deleteById(customerId);
    }

    @PostMapping("/saveCustomer")
    @ResponseBody
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @GetMapping("/updateCustomer")
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

