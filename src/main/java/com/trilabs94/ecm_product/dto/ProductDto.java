package com.trilabs94.ecm_product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private LocalDateTime createdAt;
}
