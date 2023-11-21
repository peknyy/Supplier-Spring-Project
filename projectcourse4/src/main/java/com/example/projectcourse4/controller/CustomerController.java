package com.example.projectcourse4.controller;

import com.example.projectcourse4.DTO.CustomerRequest;
import com.example.projectcourse4.entity.Customer;
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
        Optional<Customer> optionalCustomer = customerService.findByCustomerName(customerName);

        if (optionalCustomer.isPresent()) {
            return new ResponseEntity<Optional<Customer>>(optionalCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<Optional<Customer>> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity<Optional<Customer>>((Optional<Customer>) null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/saveCustomer")
    public HttpStatus addCustomer(@RequestBody CustomerRequest customer) {
        if (customerService.save(customer) != null){
            return HttpStatus.CREATED;
        }

        return HttpStatus.NOT_FOUND;
    }
    @PutMapping("/updateCustomer")
    public HttpStatus updateCustomer(@RequestBody CustomerRequest customer) {
        if (customerService.update(customer) != null){
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_FOUND;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}

