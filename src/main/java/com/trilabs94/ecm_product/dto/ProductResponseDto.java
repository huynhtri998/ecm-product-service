package com.trilabs94.ecm_product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;

    private String name;

    private String description;

    private Integer availableQuantity;

    private BigDecimal price;

    private Long categoryId;

    private String categoryName;

    private OffsetDateTime createdAt;
}
