package com.trilabs94.ecm_product.service.impl;

import com.trilabs94.common_error_handler.exception.ResourceNotFoundException;
import com.trilabs94.ecm_product.dto.ProductRequestDto;
import com.trilabs94.ecm_product.dto.ProductResponseDto;
import com.trilabs94.ecm_product.entity.Category;
import com.trilabs94.ecm_product.entity.Product;
import com.trilabs94.ecm_product.mapper.ProductMapper;
import com.trilabs94.ecm_product.repository.CategoryRepository;
import com.trilabs94.ecm_product.repository.ProductRepository;
import com.trilabs94.ecm_product.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        log.info("Creating new product with name={}, categoryId={}",
                requestDto.getName(), requestDto.getCategoryId());

        Category category = getCategoryOrThrow(requestDto.getCategoryId());

        Product product = productMapper.toEntity(requestDto, category);
        Product saved = productRepository.save(product);

        return productMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        log.info("Updating product id={}", id);

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id %d not found".formatted(id)));

        Category category = getCategoryOrThrow(requestDto.getCategoryId());

        productMapper.updateEntity(existing, requestDto, category);
        Product saved = productRepository.save(existing);

        return productMapper.toResponseDto(saved);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        log.info("Fetching product id={}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id %d not found".formatted(id)));

        return productMapper.toResponseDto(product);
    }

    @Override
    public Page<ProductResponseDto> getProducts(Pageable pageable) {
        log.info("Fetching products with pageable={}", pageable);

        Page<Product> page = productRepository.findAll(pageable);
        return page.map(productMapper::toResponseDto);
    }

    @Override
    public Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable) {
        log.info("Fetching products by categoryId={} with pageable={}", categoryId, pageable);

        getCategoryOrThrow(categoryId);

        Page<Product> page = productRepository.findByCategoryId(categoryId, pageable);
        return page.map(productMapper::toResponseDto);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product id={}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id %d not found".formatted(id)));

        productRepository.delete(product);
    }

    private Category getCategoryOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id %d not found".formatted(categoryId)));
    }
}
