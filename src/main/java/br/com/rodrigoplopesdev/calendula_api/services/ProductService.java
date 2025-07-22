package br.com.rodrigoplopesdev.calendula_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.exceptions.BusinessException;
import br.com.rodrigoplopesdev.calendula_api.exceptions.EntityNotFoundException;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.patterns.factory.ProductFactory;
import br.com.rodrigoplopesdev.calendula_api.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(CreateProductDTO product) {
        Product instance = ProductFactory.getInstance(product);
        log.info("Product Service -> {}", instance.getTitle());

        if (this.productRepository.existsByTitle(instance.getTitle())) {
            throw new BusinessException("Product already registered");
        }
        return this.productRepository.save(instance);
    }

    public Product findById(String id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product not found with %s", id)));
    }

    public Object update(String id, CreateProductDTO data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
