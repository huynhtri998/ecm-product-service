package com.trilabs94.ecm_product.repository;

import com.trilabs94.ecm_product.dto.CategoryDto;
import com.trilabs94.ecm_product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
    boolean existsByName(String name);
}
