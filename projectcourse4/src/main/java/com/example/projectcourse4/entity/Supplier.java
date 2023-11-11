package com.example.projectcourse4.entity;

import com.example.projectcourse4.token.Token;
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
@Table(name = "supplier")
public class Supplier {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @Column(name = "name", nullable = false)
    private String supplierName;

    @Column(name = "email", nullable = false)
    private String supplierEmail;

    @Column(name = "contact_person", nullable = false)
    private Long contactPerson;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @JsonManagedReference
    @OneToMany(mappedBy = "supplier_id")
    private List<Product> products;
}
