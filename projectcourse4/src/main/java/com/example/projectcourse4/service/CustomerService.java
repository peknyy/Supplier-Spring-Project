package com.example.projectcourse4.service;

import com.example.projectcourse4.DTO.CustomerRequest;
import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findByCustomerName(String customerName) {
        try {
            return Optional.ofNullable(customerRepository.findByCustomerName(customerName));
        } catch (Exception e) {
            // Handle the exception or log it
            logger.error("Error finding customer by name: {}", customerName, e);
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findByCustomerId(Long customerId) {
        try {
            return customerRepository.findById(customerId);
        } catch (Exception e) {
            // Handle the exception or log it
            logger.error("Error finding customer by ID: {}", customerId, e);
            return Optional.empty();
        }
    }

    @Transactional
    public Iterable<Customer> getAll() {
        try {
            return customerRepository.findAll();
        } catch (Exception e) {
            // Handle the exception or log it
            logger.error("Error getting all customers", e);
            return null;
        }
    }

    @Transactional
    public Customer save(CustomerRequest customer) {
        try {
            Customer customer1 = Customer.builder()
                    .customerEmail(customer.getCustomerEmail())
                    .customerName(customer.getCustomerName())
                    .customerPhoneNumber(customer.getCustomerPhoneNumber())
                    .userId(customer.getUserId())
                    .build();
            return customerRepository.save(customer1);
        } catch (Exception e) {

            logger.error("Error saving customer: {}", customer, e);
            return null;
        }
    }

    @Transactional
    public Customer update(CustomerRequest customer) {
        try {
            Customer customer1 = Customer.builder()
                    .customerEmail(customer.getCustomerEmail())
                    .customerName(customer.getCustomerName())
                    .customerPhoneNumber(customer.getCustomerPhoneNumber())
                    .customerId(customer.getUserId())
                    .build();
            return customerRepository.save(customer1);
        } catch (Exception e) {
            // Handle the exception or log it
            logger.error("Error updating customer: {}", customer, e);
            return null;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            // Handle the exception or log it
            logger.error("Error deleting customer by ID: {}", id, e);
        }
    }
}
