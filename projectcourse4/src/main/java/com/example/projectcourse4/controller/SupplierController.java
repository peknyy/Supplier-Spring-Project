package com.example.projectcourse4.controller;

import com.example.projectcourse4.configuration.JwtService;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.service.SupplierService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/api/v1/suppliers")
@PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
public class SupplierController {
//    public boolean hasUserRole() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SUPPLIER"));
//    }3

    private final SupplierService supplierService;
    private final JwtService jwtService;

    @Autowired
    public SupplierController(SupplierService supplierService, JwtService jwtService) {
        this.supplierService = supplierService;
        this.jwtService = jwtService;
    }

//    @GetMapping
//    public Iterable<Supplier> getAll () {
//        return supplierService.getAll();
//    }

    @GetMapping("/findBySupplierName/{supplierName}")
    public ResponseEntity<Supplier> findBySupplierName (@PathVariable String supplierName) {
        return new ResponseEntity<Supplier>(supplierService.findBySupplierName(supplierName),HttpStatus.OK);
    }

    @GetMapping("/findAllProductsForSupplier/{supplierName}")
    public ResponseEntity<List<Product>> findAllProductsForSupplier ( @PathVariable String supplierName) {

            return new ResponseEntity<List<Product>>(supplierService.findAllProductsForSupplier(supplierName), HttpStatus.OK);

    }

//    @DeleteMapping("/deleteSupplier")
//    public void deleteSupplier(Long supplierId) {
//        supplierService.deleteById(supplierId);
//    }

//    @PostMapping("/saveSupplier")
////    @Secured("SUPPLIER")
//    public Supplier addSupplier(@RequestBody Supplier supplier) {
//        return supplierService.save(supplier);
//    }

    @PutMapping("/updateSupplier")
    public Supplier updateSupplier(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Supplier supplier){
        return supplierService.update(supplier);
    }


    @GetMapping("/test")
//    @PreAuthorize("hasAnyAuthority('admin:read','supplier:read')")
    public String test(){
        return "test";
    }

}

