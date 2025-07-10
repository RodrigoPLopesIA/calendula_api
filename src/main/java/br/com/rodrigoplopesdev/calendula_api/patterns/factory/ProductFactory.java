package br.com.rodrigoplopesdev.calendula_api.patterns.factory;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;

public class ProductFactory {


    public static Product getInstance(CreateProductDTO data) {
        return  Product.builder()
                        .title(data.title())
                        .description(data.description())
                        .price(data.price())
                        .colors(data.colors())
                        .build();
    }
    

}
