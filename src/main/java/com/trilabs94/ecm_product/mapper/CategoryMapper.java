package com.trilabs94.ecm_product.mapper;

import com.trilabs94.ecm_product.dto.CategoryRequestDto;
import com.trilabs94.ecm_product.dto.CategoryResponseDto;
import com.trilabs94.ecm_product.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDto dto) {
        if (dto == null) return null;

        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public void updateEntity(Category category, CategoryRequestDto dto) {
        if (category == null || dto == null) return;

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
    }

    public CategoryResponseDto toResponseDto(Category entity) {
        if (entity == null) return null;

        return CategoryResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
