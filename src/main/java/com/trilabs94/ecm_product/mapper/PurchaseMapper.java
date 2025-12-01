package com.trilabs94.ecm_product.mapper;

import com.trilabs94.ecm_product.dto.ItemPurchaseResponse;
import com.trilabs94.ecm_product.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PurchaseMapper {

    public ItemPurchaseResponse toItemResponse(Product product, Integer quantity) {
        if (product == null || quantity == null) {
            return null;
        }

        BigDecimal unitPrice = product.getPrice();
        BigDecimal subtotal = calcSubtotal(unitPrice, quantity);

        return ItemPurchaseResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .quantity(quantity)
                .unitPrice(unitPrice)
                .subtotal(subtotal)
                .build();
    }

    public BigDecimal calcSubtotal(BigDecimal price, Integer quantity) {
        if (price == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
