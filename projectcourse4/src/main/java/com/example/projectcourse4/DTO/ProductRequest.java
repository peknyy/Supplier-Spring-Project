package com.example.projectcourse4.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

  private String productName;
  private String productDesc;
  private int productPrice;
  private String imageUrl;
  private Long category;
  private Boolean available;
}