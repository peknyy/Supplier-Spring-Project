package com.example.projectcourse4.service;

import com.example.projectcourse4.DTO.ProductRequest;
import com.example.projectcourse4.configuration.JwtService;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.repository.ProductRepository;
import com.example.projectcourse4.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final JwtService jwtService;

    private final SupplierRepository supplierRepository;

    @Autowired // constructor inject
    public ProductService(ProductRepository productRepository, JwtService jwtService, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.jwtService = jwtService;
        this.supplierRepository = supplierRepository;
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

    public Product save(String authorizationHeader,ProductRequest productRequest) {
        final String jwt;
        if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        jwt = authorizationHeader.substring(7);
        var product = Product.builder()
                .productDesc(productRequest.getProductDesc())
                .productPrice(productRequest.getProductPrice())
                .productName(productRequest.getProductName())
                .category(productRequest.getCategory())
                .imageUrl(productRequest.getImageUrl())
                .available(productRequest.getAvailable())
                .supplier_id(supplierRepository.findBySupplierName(jwtService.extractUsername(jwt)))
                .build();
        return productRepository.save(product);
    }

    public Product update(String authorizationHeader,ProductRequest productRequest, Long productId) {
        final String jwt;
        if (authorizationHeader == null ||!authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        jwt = authorizationHeader.substring(7);
        var supplier = supplierRepository.findBySupplierName(jwtService.extractUsername(jwt));

        if (productRepository.findById(productId).isPresent()) {
            var product = Product.builder()
                    .productId(productId)
                    .productDesc(productRequest.getProductDesc())
                    .productPrice(productRequest.getProductPrice())
                    .productName(productRequest.getProductName())
                    .category(productRequest.getCategory())
                    .imageUrl(productRequest.getImageUrl())
                    .available(productRequest.getAvailable())
                    .supplier_id(supplier)
                    .build();
            return productRepository.save(product);
        }
        else return null;


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
