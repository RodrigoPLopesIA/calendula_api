package br.com.rodrigoplopesdev.calendula_api.dtos;


import br.com.rodrigoplopesdev.calendula_api.models.Product;

public record ProductDTO(String id, String title, String description, Double price) {

    public ProductDTO(Product product){
        this(product.getId() , product.getTitle(), product.getDescription(), product.getPrice());
    }
}
