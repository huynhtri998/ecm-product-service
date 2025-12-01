package com.trilabs94.ecm_product.controller;

import com.trilabs94.ecm_product.dto.ProductPurchaseRequest;
import com.trilabs94.ecm_product.dto.ProductPurchaseResponse;
import com.trilabs94.ecm_product.service.impl.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/purchase")
    public ProductPurchaseResponse purchaseProducts(
            @Valid @RequestBody ProductPurchaseRequest request
    ) {
        return inventoryService.purchaseProducts(request);
    }
}
