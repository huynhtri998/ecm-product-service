package com.trilabs94.ecm_product.repository;

import com.trilabs94.ecm_product.dto.CategoryDto;
import com.trilabs94.ecm_product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category,Long> {

    @Query("""
            SELECT c FROM Category c
            LEFT JOIN FETCH c.products p
    """)
    List<Category> findCategoryWithProducts();

    boolean existsByName(String name);
}
