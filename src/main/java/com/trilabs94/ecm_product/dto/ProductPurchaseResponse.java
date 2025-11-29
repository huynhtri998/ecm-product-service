package com.trilabs94.ecm_product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPurchaseResponse {
    private Long productId;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
}
