package com.trilabs94.ecm_product.service.impl;

import com.trilabs94.common_error_handler.exception.ResourceNotFoundException;
import com.trilabs94.ecm_product.dto.CategoryDto;
import com.trilabs94.ecm_product.entity.Category;
import com.trilabs94.ecm_product.mapper.CategoryMapper;
import com.trilabs94.ecm_product.repository.ICategoryRepository;
import com.trilabs94.ecm_product.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::toCategoryDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found with id: " + id)
                );
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if(categoryRepository.existsByName(categoryDto.getName())){
            throw new ResourceNotFoundException("Category already exists with name: " + categoryDto.getName());
        } else {
            Category category = CategoryMapper.toCategory(categoryDto);
            Category savedCategory = categoryRepository.save(category);
            return CategoryMapper.toCategoryDto(savedCategory);
        }
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(categoryDto.getName());
                    existingCategory.setDescription(categoryDto.getDescription());
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    return CategoryMapper.toCategoryDto(updatedCategory);
                })
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found with id: " + id)
                );
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        } else {
            categoryRepository.deleteById(id);
        }
    }
}
