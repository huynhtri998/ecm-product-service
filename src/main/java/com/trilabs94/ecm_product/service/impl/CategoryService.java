package com.trilabs94.ecm_product.service.impl;

import com.trilabs94.common_error_handler.exception.ResourceAlreadyExistsException;
import com.trilabs94.common_error_handler.exception.ResourceNotFoundException;
import com.trilabs94.ecm_product.dto.CategoryRequestDto;
import com.trilabs94.ecm_product.dto.CategoryResponseDto;
import com.trilabs94.ecm_product.entity.Category;
import com.trilabs94.ecm_product.mapper.CategoryMapper;
import com.trilabs94.ecm_product.repository.CategoryRepository;
import com.trilabs94.ecm_product.repository.ProductRepository;
import com.trilabs94.ecm_product.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        log.info("Creating new category with name={}", requestDto.getName());

        if (categoryRepository.existsByNameIgnoreCase(requestDto.getName())) {
            throw new ResourceAlreadyExistsException(
                    "Category with name '%s' already exists".formatted(requestDto.getName())
            );
        }

        Category category = categoryMapper.toEntity(requestDto);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto requestDto) {
        log.info("Updating category id={}", id);

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id %d not found".formatted(id)));

        categoryMapper.updateEntity(existing, requestDto);
        Category saved = categoryRepository.save(existing);

        return categoryMapper.toResponseDto(saved);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        log.info("Fetching category id={}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id %d not found".formatted(id)));

        return categoryMapper.toResponseDto(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        log.info("Fetching all categories");

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        log.info("Deleting category id={}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id %d not found".formatted(id)));

        if (productRepository.existsByCategoryId(id)) {
            throw new ResourceAlreadyExistsException(
                    "Cannot delete category id %d because there are existing products using this category"
                            .formatted(id)
            );
        }

        categoryRepository.delete(category);
    }
}
