package com.trilabs94.ecm_product.mapper;

import com.trilabs94.ecm_product.dto.ProductRequestDto;
import com.trilabs94.ecm_product.dto.ProductResponseDto;
import com.trilabs94.ecm_product.entity.Category;
import com.trilabs94.ecm_product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto, Category category) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .availableQuantity(dto.getAvailableQuantity())
                .price(dto.getPrice())
                .category(category)
                .build();
    }

    public void updateEntity(Product entity, ProductRequestDto dto, Category category) {
        if (entity == null || dto == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAvailableQuantity(dto.getAvailableQuantity());
        entity.setPrice(dto.getPrice());
        entity.setCategory(category);
    }

    public ProductResponseDto toResponseDto(Product entity) {
        if (entity == null) return null;

        return ProductResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .availableQuantity(entity.getAvailableQuantity())
                .price(entity.getPrice())
                .categoryId(entity.getCategory().getId())
                .categoryName(entity.getCategory().getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
