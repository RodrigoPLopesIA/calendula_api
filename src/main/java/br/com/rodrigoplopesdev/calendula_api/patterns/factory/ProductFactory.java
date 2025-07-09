package br.com.rodrigoplopesdev.calendula_api.patterns.factory;

import java.util.Arrays;
import java.util.UUID;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;

public class ProductFactory {


    public static Product getInstance(CreateProductDTO data) {
        return  Product.builder()
                        .id(UUID.randomUUID())
                        .title(data.title())
                        .description(data.description())
                        .price(data.price())
                        .cores(data.cores())
                        .build();
    }

}
