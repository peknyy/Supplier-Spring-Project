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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final JwtService jwtService;
    private final SupplierRepository supplierRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, JwtService jwtService, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.jwtService = jwtService;
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findByProductId(Long productId) {
        return productRepository.findById(productId);
    }

    @Transactional
    public Optional<Iterable<Product>> getAll() {
        return Optional.of(productRepository.findAll());
    }

    @Transactional
    public Product save(String authorizationHeader, ProductRequest productRequest) {
        try {
            final String jwt;
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Invalid authorization header");
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
        } catch (Exception e) {
            logger.error("Error saving product: {}", e.getMessage());
            return null;
        }
    }

    @Transactional
    public Product update(String authorizationHeader, ProductRequest productRequest, Long productId) {
        try {
            final String jwt;
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Invalid authorization header");
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
            } else {
                throw new RuntimeException("Product not found");
            }
        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage());
            return null;
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting product: {}", e.getMessage());
            throw new RuntimeException("Error deleting product", e);
        }
    }
}
