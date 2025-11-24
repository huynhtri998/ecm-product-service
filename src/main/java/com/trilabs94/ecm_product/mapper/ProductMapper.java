package com.trilabs94.ecm_product.mapper;

import com.trilabs94.ecm_product.dto.ProductDto;
import com.trilabs94.ecm_product.entity.Product;

public class ProductMapper {

    public static ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public static Product mapToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
