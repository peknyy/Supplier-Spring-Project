package com.example.projectcourse4.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Table(name = "customer")
public class Customer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "name", nullable = false)
    private String customerName;

    @Column(name = "email", nullable = false)
    private String customerEmail;

    @Column(name = "phone_number", nullable = false)
    private Long customerPhoneNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;


}
