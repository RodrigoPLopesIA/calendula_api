package br.com.rodrigoplopesdev.calendula_api.services;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.patterns.factory.ProductFactory;
import br.com.rodrigoplopesdev.calendula_api.repositories.ProductRepository;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Product Service -> should create a new product")
    public void shouldCreateNewProduct() {
        CreateProductDTO data = new CreateProductDTO("Test", "test", Arrays.asList("Azul", "Verde"), 25.50);
        Product instance = ProductFactory.getInstance(data);

        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(instance);

        var result = productService.save(data);

        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(instance.getTitle());
    }

    
}
