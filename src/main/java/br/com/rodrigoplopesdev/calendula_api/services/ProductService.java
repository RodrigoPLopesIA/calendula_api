package br.com.rodrigoplopesdev.calendula_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.exceptions.BusinessException;
import br.com.rodrigoplopesdev.calendula_api.exceptions.DuplicatedTitleException;
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

    public Product update(String id, CreateProductDTO data) {
        var product = this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id $s not found.", id)));

        var existingProduct = this.productRepository.existsByTitle(data.title())
                && !data.title().equals(product.getTitle());

        if (existingProduct)
            throw new DuplicatedTitleException(
                    String.format("Product with this title %s already exists.", data.title()));

        product.setTitle(data.title());
        product.setDescription(data.description());
        product.setColors(data.colors());

        return this.productRepository.save(product);
    }

    public void delete(String id) {
        var product = this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s not exists.", id)));

        this.productRepository.delete(product);
    }

    public Page<Product> findAll(Pageable page) {
        return productRepository.findAll(page);
    }

}
