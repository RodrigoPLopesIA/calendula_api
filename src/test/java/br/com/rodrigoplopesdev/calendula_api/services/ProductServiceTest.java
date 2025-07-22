package br.com.rodrigoplopesdev.calendula_api.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductDTO;
import br.com.rodrigoplopesdev.calendula_api.exceptions.BusinessException;
import br.com.rodrigoplopesdev.calendula_api.exceptions.EntityNotFoundException;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.patterns.factory.ProductFactory;
import br.com.rodrigoplopesdev.calendula_api.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Product Service -> should create a new product")
    public void shouldCreateNewProduct() {

        CreateProductDTO data = new CreateProductDTO("Test", "test", Arrays.asList("Azul", "Verde"), 25.50);
        ProductDTO responseDTO = new ProductDTO("asdasdasd", "Test", "test", Arrays.asList("Azul", "Verde"), 25.50);

        Product instance = ProductFactory.getInstance(data);
        Product response = ProductFactory.getInstance(responseDTO);

        Mockito.when(productRepository.existsByTitle(Mockito.anyString())).thenReturn(false);
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(response);

        var result = productService.save(data);

        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getTitle()).isEqualTo(instance.getTitle());
    }

    @Test
    @DisplayName("Product Service -> should throw a exception when create a product")
    public void shouldThrowAExceptionWhenCreateAProduct() {
        CreateProductDTO data = new CreateProductDTO("Test", "test", Arrays.asList("Azul", "Verde"), 25.50);
        Product instance = ProductFactory.getInstance(data);
        Mockito.when(productRepository.existsByTitle(Mockito.anyString())).thenReturn(true);

        var exception = Assertions.catchThrowable(() -> productService.save(data));

        Assertions.assertThat(exception).isInstanceOf(BusinessException.class);
        Mockito.verify(productRepository, Mockito.never()).save(instance);
    }

    @Test
    @DisplayName("Product Service -> should return a product by id")
    public void shouldReturnProductById() {
        var id = "1234";
        CreateProductDTO data = new CreateProductDTO("Test", "test", Arrays.asList("Azul", "Verde"), 25.50);
        Product instance = ProductFactory.getInstance(data);
        instance.setId(id);

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(instance));

        var result = productService.findById(id);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo("1234");
        Assertions.assertThat(result.getTitle()).isEqualTo("Test");
        Assertions.assertThat(result.getDescription()).isEqualTo("test");
        Assertions.assertThat(result.getColors()).isNotEmpty();
        Assertions.assertThat(result.getColors()).contains("Azul", "Verde");
    }

    @Test
    @DisplayName("Product Service -> should throw EntityNotFoundException when product is not found by id")
    public void shouldThrowNotFoundException() {
        var id = "1234";
        CreateProductDTO data = new CreateProductDTO("Test", "test", Arrays.asList("Azul", "Verde"), 25.50);
        Product instance = ProductFactory.getInstance(data);
        instance.setId(id);

        Mockito.when(productRepository.findById(Mockito.anyString())).thenThrow(new EntityNotFoundException("Product not found with id: 1234"));

        var result = Assertions.catchThrowable(() -> productService.findById(id));

        Assertions.assertThat(result).isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThat(result.getMessage()).isEqualTo("Product not found with id: 1234");

    }

    @Test
    @DisplayName("Product Service -> should update a product")
    public void shouldUpdateProduct(){

        String id = "3e733c92-a219-4d50-a94c-e3c700b63a5a";
        CreateProductDTO data = new CreateProductDTO("Test", "test", List.of("Azul"), 25.5);
        Product instance = ProductFactory.getInstance(data);
        instance.setId(id);

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(instance));
        Mockito.when(productRepository.save(instance)).thenReturn(instance);

        var result = productService.update(id, data);


        Assertions.assertThat(result).isNotNull();
    }

}
