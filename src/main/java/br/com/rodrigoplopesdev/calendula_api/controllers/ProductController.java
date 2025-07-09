package br.com.rodrigoplopesdev.calendula_api.controllers;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.services.ProductService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping
    public ResponseEntity create(@RequestBody CreateProductDTO data) {
        Product product = this.productService.save(data);
        return ResponseEntity.ok().body(product);
    }
    
}
