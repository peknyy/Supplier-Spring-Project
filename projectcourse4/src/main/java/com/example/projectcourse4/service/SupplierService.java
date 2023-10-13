package com.example.projectcourse4.service;

import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.repository.ProductRepository;
import com.example.projectcourse4.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierService {
    private final Logger logger = LoggerFactory.getLogger(SupplierService.class);
    private final SupplierRepository supplierRepository;

    @Autowired // constructor inject
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Optional<Supplier> findBySupplierName(String supplierName) {
        return supplierRepository.findBySupplierName(supplierName);
    }
    public Optional<Supplier> findBySupplierId(Long supplierId) {
        return supplierRepository.findById(supplierId);
    }

    public Iterable<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier update(Supplier supplier) {
        return supplierRepository.save(supplier);
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
        supplierRepository.deleteById(id);
    }

}
