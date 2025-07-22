package br.com.rodrigoplopesdev.calendula_api.repositories;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import br.com.rodrigoplopesdev.calendula_api.config.MongoConfig;
import br.com.rodrigoplopesdev.calendula_api.models.Product;

@DataMongoTest
@Import(MongoConfig.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    void shouldSaveProduct() {
        Product product = Product
                .builder()
                .title("test")
                .description("testset")
                .images(List.of("imagem1", "imagem2"))
                .colors(List.of("Azul", "Green")).price(25.02).build();

        Product saved = productRepository.save(product);

        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getTitle()).isEqualTo(product.getTitle());
    }

}
