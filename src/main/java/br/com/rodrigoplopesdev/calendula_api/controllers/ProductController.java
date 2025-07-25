package br.com.rodrigoplopesdev.calendula_api.controllers;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductFilterDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.services.ProductService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get a paginated list of products with optional filters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<Product>> index(
            @Parameter(description = "Search term to filter by product name or description") @RequestParam(required = false) String search,
            @Parameter(description = "Category to filter products") @RequestParam(required = false) String category,
            @Parameter(description = "List of colors to filter products") @RequestParam(required = false) List<String> colors,
            @Parameter(description = "Minimum price to filter products") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price to filter products") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Pagination configuration") Pageable pageable) {
        ProductFilterDTO filter = new ProductFilterDTO(search, category, colors, minPrice, maxPrice);
        Page<Product> result = productService.findAll(filter, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody CreateProductDTO data) {
        Product product = this.productService.save(data);
        URI uri = UriComponentsBuilder.fromUriString("api/v1/products/{id}").buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProductDTO(product));
    }

    @Operation(summary = "Get a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> show(@PathVariable String id) {
        return ResponseEntity.ok().body(new ProductDTO(this.productService.findById(id)));
    }

    @Operation(summary = "Update an existing product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable String id, @Valid @RequestBody CreateProductDTO data) {
        return ResponseEntity.ok().body(new ProductDTO(this.productService.update(id, data)));
    }

}
