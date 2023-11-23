package com.example.projectcourse4.service;

import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.repository.ProductRepository;
import com.example.projectcourse4.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final Logger logger = LoggerFactory.getLogger(SupplierService.class);
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public Supplier findBySupplierName(String supplierName) {
        try {
            return supplierRepository.findBySupplierName(supplierName);
        } catch (Exception e) {
            logger.error("Error finding supplier by name: {}", e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsForSupplier(String supplierName) {
        try {
            Supplier supplier = supplierRepository.findBySupplierName(supplierName);
            return supplier != null ? supplier.getProducts() : null;
        } catch (Exception e) {
            logger.error("Error finding products for supplier: {}", e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Optional<Supplier> findBySupplierId(Long supplierId) {
        try {
            return supplierRepository.findById(supplierId);
        } catch (Exception e) {
            logger.error("Error finding supplier by ID: {}", e.getMessage());
            return null;
        }
    }

    @Transactional
    public Iterable<Supplier> getAll() {
        try {
            return supplierRepository.findAll();
        } catch (Exception e) {
            logger.error("Error getting all suppliers: {}", e.getMessage());
            return null;
        }
    }

    @Transactional
    public Supplier save(Supplier supplier) {
        try {
            return supplierRepository.save(supplier);
        } catch (Exception e) {
            logger.error("Error saving supplier: {}", e.getMessage());
            return null;
        }
    }

    @Transactional
    public Supplier update(Supplier supplier) {
        try {
            return supplierRepository.save(supplier);
        } catch (Exception e) {
            logger.error("Error updating supplier: {}", e.getMessage());
        }
        return null;
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            supplierRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting supplier by ID: {}", e.getMessage());
        }
    }
}
