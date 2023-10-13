package com.example.projectcourse4.controller;

import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.service.ProductService;
import com.example.projectcourse4.service.SupplierService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
//    public boolean hasUserRole() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SUPPLIER"));
//    }

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

//    @GetMapping
//    public Iterable<Supplier> getAll () {
//        return supplierService.getAll();
//    }

    @GetMapping("/findBySupplierName/{supplierName}")
    public ResponseEntity<Optional<Supplier>> findBySupplierName (@PathVariable String supplierName) {
        return new ResponseEntity<Optional<Supplier>>(supplierService.findBySupplierName(supplierName),HttpStatus.OK);
    }

    @DeleteMapping("/deleteSupplier")
    public void deleteSupplier(Long supplierId) {
        supplierService.deleteById(supplierId);
    }

    @PostMapping("/saveSupplier")
//    @Secured("SUPPLIER")
    public Supplier addSupplier(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/updateSupplier")
    public Supplier updateSupplier(@RequestBody Supplier supplier){
        return supplierService.update(supplier);
    }

    @GetMapping("/test")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPPLIER')")
    public String test(){
        return "test";
    }

}

