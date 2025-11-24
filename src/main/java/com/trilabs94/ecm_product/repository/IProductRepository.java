package com.trilabs94.ecm_product.repository;

import com.trilabs94.ecm_product.entity.Category;
import com.trilabs94.ecm_product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
