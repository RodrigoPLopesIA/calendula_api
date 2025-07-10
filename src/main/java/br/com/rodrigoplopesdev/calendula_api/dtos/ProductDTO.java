package br.com.rodrigoplopesdev.calendula_api.dtos;


import java.util.List;

import br.com.rodrigoplopesdev.calendula_api.models.Product;

public record ProductDTO(String id, String title, String description, List<String> colors, Double price) {

    public ProductDTO(Product product){
        this(product.getId() , product.getTitle(), product.getDescription(), product.getColors(), product.getPrice());
    }
}
