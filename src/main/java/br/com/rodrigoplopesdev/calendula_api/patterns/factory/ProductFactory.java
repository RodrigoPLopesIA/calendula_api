package br.com.rodrigoplopesdev.calendula_api.patterns.factory;

import java.util.UUID;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;

public class ProductFactory {


    public static Product getInstance(CreateProductDTO data) {
        return  Product.builder()
                        .id("722dc867-ea3d-40a5-936d-70e29e830b99")
                        .title(data.title())
                        .description(data.description())
                        .price(data.price())
                        .colors(data.colors())
                        .build();
    }

}
