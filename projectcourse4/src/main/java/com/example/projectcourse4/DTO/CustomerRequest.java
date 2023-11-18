package com.example.projectcourse4.DTO;

import com.example.projectcourse4.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    private String customerName;
    private String customerEmail;
    private Long customerPhoneNumber;
    private Long userId;
}