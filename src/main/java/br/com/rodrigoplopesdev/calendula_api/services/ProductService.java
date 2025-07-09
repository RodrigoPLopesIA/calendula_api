package br.com.rodrigoplopesdev.calendula_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.patterns.factory.ProductFactory;
import br.com.rodrigoplopesdev.calendula_api.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public Product save(CreateProductDTO product) {
        Product instance = ProductFactory.getInstance(product);;
        return this.productRepository.save(instance);
    }
    


    
}
