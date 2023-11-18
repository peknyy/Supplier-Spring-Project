package com.example.projectcourse4.controller;

import com.example.projectcourse4.DTO.CustomerRequest;
import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.entity.User;
import com.example.projectcourse4.service.CustomerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("/api/v1/customers")
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

    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    @GetMapping("/findByCustomerName/{customerName}")
    public ResponseEntity<Optional<Customer>> findByCustomerName(@PathVariable String customerName) {
        return new ResponseEntity<Optional<Customer>>(customerService.findByCustomerName(customerName), HttpStatus.OK);
    }


    @DeleteMapping("/deleteCustomer")
    public void deleteCustomer(Long customerId) {
        customerService.deleteById(customerId);
    }

    @PostMapping("/saveCustomer")

    public Customer addCustomer(@RequestBody CustomerRequest customer) {
        return customerService.save(customer);
    }
    @PutMapping("/updateCustomer")
    public Customer updateCustomer(@RequestBody CustomerRequest customer) {
        return customerService.update(customer);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}

