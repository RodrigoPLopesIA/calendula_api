package br.com.rodrigoplopesdev.calendula_api.repositories;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.rodrigoplopesdev.calendula_api.models.Product;

@ExtendWith(SpringExtension.class)
@DataMongoTest
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
                .colors(List.of("Azul", "Green")).price(25.02).build();

        Product saved = productRepository.save(product);

        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getTitle()).isEqualTo(product.getTitle());
    }

}
