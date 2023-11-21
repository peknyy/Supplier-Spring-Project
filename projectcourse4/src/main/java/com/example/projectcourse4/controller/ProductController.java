package com.example.projectcourse4.controller;

import com.example.projectcourse4.DTO.ProductRequest;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Optional> getAll () {
        return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/findByProductName/{productName}")
    public ResponseEntity<Optional<Product>> findByProductName (@PathVariable String productName) {
        return new ResponseEntity<Optional<Product>>(productService.findByProductName(productName), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    @DeleteMapping("/deleteProduct/{productId}")
    public HttpStatus deleteProduct(@PathVariable Long productId) {
        productService.deleteById(productId);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    @PostMapping("/saveProduct")
    @ResponseBody
    public HttpStatus addProduct(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProductRequest product) {
        if (productService.save(authorizationHeader,product) == null){
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    @PutMapping("/updateProduct/{productId}")
    public HttpStatus updateProduct(@PathVariable Long productId, @RequestHeader("Authorization") String authorizationHeader,@RequestBody ProductRequest product){
        if (productService.update(authorizationHeader,product, productId) == null){
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPPLIER')")
    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

