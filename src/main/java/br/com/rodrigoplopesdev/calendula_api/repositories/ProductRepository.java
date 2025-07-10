package br.com.rodrigoplopesdev.calendula_api.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rodrigoplopesdev.calendula_api.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByTitle(String title);
}
