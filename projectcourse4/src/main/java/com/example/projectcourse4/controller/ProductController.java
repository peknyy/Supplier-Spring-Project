package com.example.projectcourse4.controller;

import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.entity.Order;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.service.CustomerService;
import com.example.projectcourse4.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Iterable<Product> getAll () {
        return productService.getAll();
    }

    @GetMapping("/findByProductName/{productName}")
    public ResponseEntity<Optional<Product>> findByProductName (@PathVariable String productName) {
        return new ResponseEntity<Optional<Product>>(productService.findByProductName(productName), HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct")
    public void deleteProduct(Long productId) {
        productService.deleteById(productId);
    }

    @PostMapping("/saveProduct")
    @ResponseBody
    public Product addProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("/updateProduct")
    public Product updateProduct(@RequestBody Product product){
        return productService.update(product);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

