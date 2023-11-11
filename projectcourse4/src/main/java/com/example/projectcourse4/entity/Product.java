package com.example.projectcourse4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Builder
@Getter
@Setter
@Table(name = "product")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "name", nullable = false)
    private String productName;

    @Column(name = "description", nullable = false)
    private String productDesc;

    @Column(name = "price", nullable = false)
    private int productPrice;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier_id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "category", nullable = false)
    private Long category;

    @Column(name = "available", nullable = false)
    private Boolean available;

//    @JsonBackReference
//    @ManyToMany(mappedBy = "products")
//    private List<Order> orders;


}
