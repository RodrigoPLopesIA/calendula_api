package br.com.rodrigoplopesdev.calendula_api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductFilterDTO;
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

    @Autowired
    private MongoTemplate mongoTemplate;

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

    public Page<Product> findAll(ProductFilterDTO filter, Pageable pageable) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (filter.search() != null && !filter.search().isBlank()) {
            var regex = Pattern.compile(filter.search(), Pattern.CASE_INSENSITIVE);
            criteriaList.add(new Criteria().orOperator(
                    Criteria.where("title").regex(regex),
                    Criteria.where("description").regex(regex),
                    Criteria.where("price").regex(regex),
                    Criteria.where("category").regex(regex),
                    Criteria.where("colors").regex(regex),
                    Criteria.where("size").regex(regex)));
        }

        if (filter.category() != null && !filter.category().isBlank()) {
            criteriaList.add(Criteria.where("category").is(filter.category()));
        }

        if (filter.colors() != null && !filter.colors().isEmpty()) {
            criteriaList.add(Criteria.where("colors").in(filter.colors()));
        }

        if (filter.minPrice() != null || filter.maxPrice() != null) {
            Criteria priceCriteria = Criteria.where("price");
            if (filter.minPrice() != null) {
                priceCriteria = priceCriteria.gte(filter.minPrice());
            }
            if (filter.maxPrice() != null) {
                priceCriteria = priceCriteria.lte(filter.maxPrice());
            }
            criteriaList.add(priceCriteria);
        }


        Criteria finalCriteria = new Criteria();
        if (!criteriaList.isEmpty()) {
            finalCriteria.andOperator(criteriaList.toArray(new Criteria[0]));
        }

        Query query = new Query(finalCriteria).with(pageable);

        List<Product> products = mongoTemplate.find(query, Product.class);
        long total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Product.class);

        return new PageImpl<>(products, pageable, total);
    }

}
