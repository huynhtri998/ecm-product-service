package com.trilabs94.ecm_product.service;

import com.trilabs94.ecm_product.dto.ProductPurchaseRequest;
import com.trilabs94.ecm_product.dto.ProductPurchaseResponse;

public interface IInventoryService {
    ProductPurchaseResponse purchaseProducts(ProductPurchaseRequest request);
}
