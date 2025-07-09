package br.com.rodrigoplopesdev.calendula_api.controllers;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @PostMapping
    public ResponseEntity create(@RequestBody CreateProductDTO data) {
        ProductDTO productDTO = new ProductDTO(UUID.fromString("722dc867-ea3d-40a5-936d-70e29e830b99"), "Test", "test", 25.50, "15cm", LocalDate.now(), LocalDate.now());
        return ResponseEntity.ok().body(productDTO);
    }
    
}
