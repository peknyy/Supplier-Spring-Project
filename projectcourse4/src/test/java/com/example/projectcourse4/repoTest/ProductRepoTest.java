package com.example.projectcourse4.repoTest;

import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.repository.ProductRepository;
import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    @Transactional
    public void testSaveProduct() {
        // Arrange
        Supplier supplier = new Supplier();
        supplier.setSupplierName("TestSupplier");
        supplier.setSupplierEmail("supplier@example.com");
        Supplier savedSupplier = supplierRepository.save(supplier);

        Product product = new Product();
        product.setProductName("TestProduct");
        product.setProductDesc("Product description");
        product.setProductPrice((int) 99.99);
        product.setSupplier_id(savedSupplier);
        product.setImageUrl("product_image.jpg");
        product.setCategory(1L);
        product.setAvailable(true);

        // Act
        Product savedProduct = productRepository.save(product);

        // Assert
        assertNotNull(savedProduct.getProductId());
        assertEquals("TestProduct", savedProduct.getProductName());
        assertEquals("Product description", savedProduct.getProductDesc());
        assertEquals((int)(99.99), savedProduct.getProductPrice());
        assertEquals(savedSupplier.getSupplierId(), savedProduct.getSupplier_id().getSupplierId());
        assertEquals("product_image.jpg", savedProduct.getImageUrl());
        assertEquals(1L, savedProduct.getCategory());
        assertTrue(savedProduct.getAvailable());
    }

    @Test
    @Transactional
    public void testFindProductById() {
        // Arrange
        Supplier supplier = new Supplier();
        supplier.setSupplierName("TestSupplier");
        supplier.setSupplierEmail("supplier@example.com");
        Supplier savedSupplier = supplierRepository.save(supplier);

        Product product = new Product();
        product.setProductName("TestProduct");
        product.setProductDesc("Product description");
        product.setProductPrice((int) 99.99);
        product.setSupplier_id(savedSupplier);
        product.setImageUrl("product_image.jpg");
        product.setCategory(1L);
        product.setAvailable(true);
        Product savedProduct = entityManager.persistAndFlush(product);

        // Act
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getProductId());

        // Assert
        assertTrue(foundProduct.isPresent());
        assertEquals("TestProduct", foundProduct.get().getProductName());
    }

    // Add more test methods for other scenarios (update, delete, etc.)
}

