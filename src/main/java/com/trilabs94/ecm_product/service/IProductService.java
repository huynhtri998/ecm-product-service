package com.trilabs94.ecm_product.service;

import com.trilabs94.ecm_product.dto.ProductPurchaseRequest;
import com.trilabs94.ecm_product.dto.ProductPurchaseResponse;
import com.trilabs94.ecm_product.dto.ProductRequestDto;
import com.trilabs94.ecm_product.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);

    Page<ProductResponseDto> getProducts(Pageable pageable);

    Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable);

    void deleteProduct(Long id);
}
