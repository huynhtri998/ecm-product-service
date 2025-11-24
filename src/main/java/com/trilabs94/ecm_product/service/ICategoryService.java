package com.trilabs94.ecm_product.service;

import com.trilabs94.ecm_product.dto.CategoryRequestDto;
import com.trilabs94.ecm_product.dto.CategoryResponseDto;

import java.util.List;

public interface ICategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto requestDto);

    CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto);

    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();

    void deleteCategory(Long id);
}
