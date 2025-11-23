package com.trilabs94.ecm_product.controller;

import com.trilabs94.ecm_product.dto.CategoryDto;
import com.trilabs94.ecm_product.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Schema(
        name = "Category Controller",
        description = "REST API for categories"
)
public class CategoryController {
    private final ICategoryService categoryService;

    @Operation(
            summary = "Get All Categories",
            description = "REST API to fetch all categories"
    )
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @Operation(
            summary = "Get Category By ID",
            description = "REST API to fetch category by its ID"
    )
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }

    @Operation(
            summary = "Create New Category",
            description = "REST API to create a new category"
    )
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok().body(categoryService.createCategory(categoryDto));
    }

    @Operation(
            summary = "Update Existing Category",
            description = "REST API to update an existing category"
    )
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok().body(categoryService.updateCategory(id, categoryDto));
    }

    @Operation(
            summary = "Delete Category",
            description = "REST API to delete a category by its ID"
    )
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
