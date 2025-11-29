package com.trilabs94.ecm_product.controller;

import com.trilabs94.ecm_product.dto.ProductPurchaseRequest;
import com.trilabs94.ecm_product.dto.ProductPurchaseResponse;
import com.trilabs94.ecm_product.dto.ProductRequestDto;
import com.trilabs94.ecm_product.dto.ProductResponseDto;
import com.trilabs94.ecm_product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final IProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Create a new product with name, description, price, quantity and category."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created",
                    content = @Content(schema = @Schema(implementation = ProductResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        ProductResponseDto created = productService.createProduct(requestDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @Operation(
            summary = "Update product",
            description = "Update an existing product by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated",
                    content = @Content(schema = @Schema(implementation = ProductResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product or category not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        ProductResponseDto updated = productService.updateProduct(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Get product by ID",
            description = "Retrieve full product details by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto dto = productService.getProductById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Get products (paged)",
            description = "Retrieve a paginated list of products."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products page",
                    content = @Content(schema = @Schema(implementation = ProductResponseDto.class))
            )
    })
    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getProducts(Pageable pageable) {
        Page<ProductResponseDto> page = productService.getProducts(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Get products by category (paged)",
            description = "Retrieve a paginated list of products that belong to a specific category."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products page",
                    content = @Content(schema = @Schema(implementation = ProductResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category not found",
                    content = @Content
            )
    })
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByCategory(
            @PathVariable Long categoryId,
            Pageable pageable
    ) {
        Page<ProductResponseDto> page = productService.getProductsByCategory(categoryId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Delete product",
            description = "Delete a product by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Product deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Purchase products",
            description = "Process the purchase of multiple products."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products purchased",
                    content = @Content(schema = @Schema(implementation = ProductPurchaseResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found",
                    content = @Content
            )
    })
    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @Valid @RequestBody List<ProductPurchaseRequest> requestBody
    ) {
        List<ProductPurchaseResponse> result = productService.purchaseProducts(requestBody);
        return ResponseEntity.ok(result);
    }
}
