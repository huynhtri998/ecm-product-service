package com.trilabs94.ecm_product.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPurchaseRequest {

    private String orderReference;

    @NotEmpty(message = "At least one item must be provided for purchase")
    @Valid
    private List<ItemPurchaseRequest> items;
}
