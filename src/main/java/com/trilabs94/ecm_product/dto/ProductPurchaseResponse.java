package com.trilabs94.ecm_product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPurchaseResponse {

    private BigDecimal totalAmount;
    private String orderReference;
    private List<ItemPurchaseResponse> items;
}
