package com.trilabs94.ecm_product.mapper;

import com.trilabs94.ecm_product.dto.CategoryDto;
import com.trilabs94.ecm_product.entity.Category;

import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .description(category.getDescription())
                .products(
                        category.getProducts()
                                .stream()
                                .map(ProductMapper::mapToProductDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }
}
