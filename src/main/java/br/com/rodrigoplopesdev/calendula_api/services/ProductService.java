package br.com.rodrigoplopesdev.calendula_api.services;

import org.springframework.stereotype.Service;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.patterns.factory.ProductFactory;

@Service
public class ProductService {

    public Product save(CreateProductDTO product) {

        return ProductFactory.getInstance(product);
    }
    


    
}
