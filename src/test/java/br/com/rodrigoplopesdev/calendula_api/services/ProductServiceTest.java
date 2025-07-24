package br.com.rodrigoplopesdev.calendula_api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductFilterDTO;
import br.com.rodrigoplopesdev.calendula_api.exceptions.BusinessException;
import br.com.rodrigoplopesdev.calendula_api.exceptions.DuplicatedTitleException;
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

    @Mock
    private MongoTemplate mongoTemplate;

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

        Mockito.when(productRepository.findById(Mockito.anyString()))
                .thenThrow(new EntityNotFoundException("Product not found with id: 1234"));

        var result = Assertions.catchThrowable(() -> productService.findById(id));

        Assertions.assertThat(result).isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThat(result.getMessage()).isEqualTo("Product not found with id: 1234");

    }

    @Test
    @DisplayName("Product Service -> should update a product")
    public void shouldUpdateProduct() {

        String id = "3e733c92-a219-4d50-a94c-e3c700b63a5a";
        CreateProductDTO data = new CreateProductDTO("Test", "test", List.of("Azul"), 25.5);
        Product instance = ProductFactory.getInstance(data);
        instance.setId(id);

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(instance));
        Mockito.when(productRepository.save(instance)).thenReturn(instance);

        var result = productService.update(id, data);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Product Service -> should Entity Not Found Exception When update a product")
    public void shouldThrowEntityNotFoundExceptionWhenUpdateProduct() {

        String id = "3e733c92-a219-4d50-a94c-e3c700b63a5a";
        CreateProductDTO data = new CreateProductDTO("Test", "test", List.of("Azul"), 25.5);
        Product instance = ProductFactory.getInstance(data);
        instance.setId(id);

        Mockito.when(productRepository.findById(Mockito.anyString()))
                .thenThrow(new EntityNotFoundException(String.format("Product with id $s not found.", id)));

        var result = Assertions.catchException(() -> productService.update(id, data));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThat(result.getMessage())
                .isEqualTo(String.format("Product with id $s not found.", id));

        Mockito.verify(productRepository, never()).save(instance);
    }

    @Test
    @DisplayName("Product Service -> should throw Unique Violation Exception when updating a product")
    public void shouldThrowUniqueViolateExceptionWhenUpdateProduct() {
        String id = "3e733c92-a219-4d50-a94c-e3c700b63a5a";

        CreateProductDTO data = new CreateProductDTO("Test2", "test", List.of("Azul"), 25.5);

        Product existingProduct = ProductFactory
                .getInstance(new CreateProductDTO("Original Title", "test", List.of("Azul"), 25.5));
        existingProduct.setId(id);

        Mockito.when(productRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(existingProduct));

        Mockito.when(productRepository.existsByTitle(data.title())).thenReturn(true);

        var result = Assertions.catchException(() -> productService.update(id, data));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isInstanceOf(DuplicatedTitleException.class);
        Assertions.assertThat(result.getMessage())
                .isEqualTo(String.format("Product with this title %s already exists.", data.title()));

        Mockito.verify(productRepository, never()).save(existingProduct);
    }

    @Test
    @DisplayName("Product Service -> should delete a product")
    public void shouldDeleteProduct() {
        String id = "3e733c92-a219-4d50-a94c-e3c700b63a5a";
        Product product = Product.builder().id(id).title("title").description("sasfa").colors(List.of("Azul"))
                .images(List.of("images")).build();

        Mockito.when(productRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(product));

        productService.delete(id);

        Mockito.verify(productRepository, times(1)).delete(product);
    }

    @Test
    @DisplayName("Product Service -> should throw entity not found exception when try to delete a product")
    public void shouldThrowEntityNotFoundExceptionWhenTryToDeleteProduct() {
        String id = "3e733c92-a219-4d50-a94c-e3c700b63a5a";
        Product product = Product.builder().id(id).title("title").description("sasfa").colors(List.of("Azul"))
                .images(List.of("images")).build();

        Mockito.when(productRepository.findById(Mockito.anyString()))
                .thenThrow(new EntityNotFoundException(String.format("Product with id %s not exists.", id)));

        var result = Assertions.catchException(() -> productService.delete(id));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isInstanceOf(EntityNotFoundException.class);
        Assertions.assertThat(result.getMessage()).isEqualTo(String.format("Product with id %s not exists.", id));
        Mockito.verify(productRepository, never()).delete(product);
    }

    @Test
    void shouldReturnFilteredProducts() {
        // Arrange
        ProductFilterDTO filter = new ProductFilterDTO(
                "Bolsa bag",
                "roupas",
                List.of("azul"),
                BigDecimal.valueOf(50.0D),
                BigDecimal.valueOf(150.0D));

        Pageable pageable = PageRequest.of(0, 10);

        Product product = product = Product.builder().id("2f44ea94-d261-4cb3-ba23-3aadff7cfa2e").colors(List.of("Azul"))
                .images(List.of("images"))
                .title("Bolsa bag").price(50.0).description("saasdasd").build();
        List<Product> productList = List.of(product);

        // Mocka find e count
        when(mongoTemplate.find(any(Query.class), eq(Product.class)))
                .thenReturn(productList);
        when(mongoTemplate.count(any(Query.class), eq(Product.class)))
                .thenReturn(1L);

        // Act
        Page<Product> result = productService.findAll(filter, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(productList, result.getContent());

        // Verifica que o mongoTemplate foi chamado corretamente
        verify(mongoTemplate).find(any(Query.class), eq(Product.class));
        verify(mongoTemplate).count(any(Query.class), eq(Product.class));
    }

}
