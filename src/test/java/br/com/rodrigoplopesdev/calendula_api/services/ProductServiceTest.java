package br.com.rodrigoplopesdev.calendula_api.services;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.patterns.factory.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Product Service -> should create a new product")
    public void shouldCreateNewProduct() {
        CreateProductDTO data = new CreateProductDTO("Test", "test", Arrays.asList("Azul", "Verde"), 25.50);
        Product product = ProductFactory.getInstance(data);


        var result = productService.save(data);


        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(product.getId());
        Assertions.assertThat(result.getTitle()).isEqualTo(product.getTitle());
        Assertions.assertThat(result.getDescription()).isEqualTo(product.getDescription());
        Assertions.assertThat(result.getPrice()).isEqualTo(product.getPrice());
        Assertions.assertThat(result.getColors()).isEqualTo(product.getColors());

    }
}
