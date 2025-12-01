package com.trilabs94.ecm_product.service.impl;


import com.trilabs94.common_error_handler.exception.BusinessException;
import com.trilabs94.common_error_handler.exception.ResourceNotFoundException;
import com.trilabs94.ecm_product.dto.ItemPurchaseRequest;
import com.trilabs94.ecm_product.dto.ItemPurchaseResponse;
import com.trilabs94.ecm_product.dto.ProductPurchaseRequest;
import com.trilabs94.ecm_product.dto.ProductPurchaseResponse;
import com.trilabs94.ecm_product.entity.Product;
import com.trilabs94.ecm_product.mapper.PurchaseMapper;
import com.trilabs94.ecm_product.repository.ProductRepository;
import com.trilabs94.ecm_product.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService implements IInventoryService {

    private final ProductRepository productRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    public ProductPurchaseResponse purchaseProducts(ProductPurchaseRequest request) {

        Map<Long, Integer> qtyByProductId = new HashMap<>();
        for (ItemPurchaseRequest item : request.getItems()) {
            qtyByProductId.merge(item.getProductId(), item.getQuantity(), Integer::sum);
        }

        List<ItemPurchaseResponse> itemResponses = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        Map<Long, Product> productMap = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : qtyByProductId.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findByIdForUpdate(productId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id: " + productId));

            if (product.getAvailableQuantity() < quantity) {
                throw new BusinessException("Insufficient stock for product id: " + productId);
            }

            productMap.put(productId, product);
        }

        for (Map.Entry<Long, Integer> entry : qtyByProductId.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productMap.get(productId);

            int newQty = product.getAvailableQuantity() - quantity;
            product.setAvailableQuantity(newQty);

            ItemPurchaseResponse itemResponse = purchaseMapper.toItemResponse(product, quantity);
            itemResponses.add(itemResponse);

            totalAmount = totalAmount.add(itemResponse.getSubtotal());
        }

        productRepository.saveAll(productMap.values());

        return ProductPurchaseResponse.builder()
                .orderReference(request.getOrderReference())
                .totalAmount(totalAmount)
                .items(itemResponses)
                .build();
    }
}