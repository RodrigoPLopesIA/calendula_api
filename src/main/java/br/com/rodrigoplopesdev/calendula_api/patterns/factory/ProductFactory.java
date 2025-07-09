package br.com.rodrigoplopesdev.calendula_api.patterns.factory;

import java.util.Arrays;
import java.util.UUID;

import br.com.rodrigoplopesdev.calendula_api.models.Product;

public class ProductFactory {


    public static Product getInstance() {
        return  Product.builder()
                        .id(UUID.randomUUID())
                        .title("Test")
                        .description("test")
                        .cores(Arrays.asList("Azul", "verde"))
                        .build();
    }

}
