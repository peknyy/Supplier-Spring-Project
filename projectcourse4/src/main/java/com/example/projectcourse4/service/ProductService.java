package com.example.projectcourse4.service;

import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.repository.CustomerRepository;
import com.example.projectcourse4.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired // constructor inject
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }
    public Optional<Product> findByProductId(Long productId) {
        return productRepository.findById(productId);
    }

    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        return productRepository.save(product);
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
        productRepository.deleteById(id);
    }

}
