package com.example.projectcourse4.service;

import com.example.projectcourse4.DTO.CustomerRequest;
import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.entity.Group;
import com.example.projectcourse4.repository.CustomerRepository;
import com.example.projectcourse4.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    @Autowired // constructor inject
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findByCustomerName(String customerName) {
        return Optional.ofNullable(customerRepository.findByCustomerName(customerName));
    }
    public Optional<Customer> findByCustomerId(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Iterable<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer save(CustomerRequest customer) {
        Customer customer1 = Customer.builder()
                .customerEmail(customer.getCustomerEmail())
                .customerName(customer.getCustomerName())
                .customerPhoneNumber(customer.getCustomerPhoneNumber())
                .customerId(customer.getUserId())
                .build();
        return customerRepository.save(customer1);
    }

    public Customer update(CustomerRequest customer) {
        Customer customer1 = Customer.builder()
                .customerEmail(customer.getCustomerEmail())
                .customerName(customer.getCustomerName())
                .customerPhoneNumber(customer.getCustomerPhoneNumber())
                .customerId(customer.getUserId())
                .build();
        return customerRepository.save(customer1);
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
        customerRepository.deleteById(id);
    }

}
