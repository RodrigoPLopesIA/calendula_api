package br.com.rodrigoplopesdev.calendula_api.controllers;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductDTO;
import br.com.rodrigoplopesdev.calendula_api.exceptions.BusinessException;
import br.com.rodrigoplopesdev.calendula_api.exceptions.DuplicatedTitleException;
import br.com.rodrigoplopesdev.calendula_api.exceptions.EntityNotFoundException;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.patterns.factory.ProductFactory;
import br.com.rodrigoplopesdev.calendula_api.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest
@AutoConfigureMockMvc
public class ProductControllerTest {

        @Autowired
        private MockMvc mvc;

        @MockBean
        private ProductService productService;

        @Test
        @DisplayName("GET /api/v1/products")
        public void shouldReturnAllProducts() throws Exception{
                var request = MockMvcRequestBuilders
                .get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON);

                mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("POST /api/v1/products")
        public void shouldSaveProduct() throws Exception {

                Product product = ProductFactory.getInstance(new ProductDTO("722dc867-ea3d-40a5-936d-70e29e830b99",
                                "Test", "test", Arrays.asList("Azul", "Verde"), 25.50));

                String json = new ObjectMapper().writeValueAsString(product);

                BDDMockito.given(productService.save(Mockito.any(CreateProductDTO.class))).willReturn(product);

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .post("/api/v1/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("id")
                                                .value("722dc867-ea3d-40a5-936d-70e29e830b99"))
                                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Test"));
        }

        @Test
        @DisplayName("POST /api/v1/products -> should throw a exception ")
        public void shouldThrowAExceptionWhenCreateAProduct() throws Exception {

                CreateProductDTO data = new CreateProductDTO(null, null, null, null);
                String json = new ObjectMapper().writeValueAsString(data);

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .post("/api/v1/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("path", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.title")
                                                .value(Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.description")
                                                .value(Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.price")
                                                .value(Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.colors")
                                                .value(Matchers.any(String.class)));

        }

        @Test
        @DisplayName("POST /api/v1/products -> should throw an error when attempting to create a product with a duplicate title")
        public void shouldThrowErrorWhenCreatingProductWithDuplicateTitle() throws Exception {
                CreateProductDTO data = new CreateProductDTO("Test", "test", Arrays.asList("Azul", "Verde"), 25.50);
                String json = new ObjectMapper().writeValueAsString(data);

                BDDMockito.given(productService.save(data))
                                .willThrow(new BusinessException("Product already registered"));
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .post("/api/v1/products")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("path", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("errors").exists());

        }

        @Test
        @DisplayName("GET /api/v1/products/{id} -> should return a product by id")
        public void shouldReturnBookById() throws Exception {

                String id = "12345789132";
                ProductDTO response = new ProductDTO("1234879", "Test", "testestset", List.of("Azul"), 25.06);
                String json = new ObjectMapper().writeValueAsString(response);
                Product product = ProductFactory.getInstance(response);

                BDDMockito.given(productService.findById(Mockito.anyString())).willReturn(product);

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/products/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("title", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("description", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("colors", Matchers.notNullValue()));

        }

        @Test
        @DisplayName("GET /api/v1/products/{id} -> should return a error if product not found")
        public void shouldReturnErrorWhenProductNotFound() throws Exception {

                String id = "12345789132";
                ProductDTO response = new ProductDTO("1234879", "Test", "testestset", List.of("Azul"), 25.06);
                String json = new ObjectMapper().writeValueAsString(response);

                BDDMockito.given(productService.findById(Mockito.anyString()))
                                .willThrow(new EntityNotFoundException("Product not found with 12345789132"));

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/products/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound())
                                .andExpect(MockMvcResultMatchers.jsonPath("path", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.any(String.class)));

        }

        @Test
        @DisplayName("PUT /api/v1/products/{id} -> should UPDATE a product by id")
        public void shouldUpdateBookById() throws Exception {

                String id = "12345789132";
                ProductDTO response = new ProductDTO("1234879", "Test", "testestset", List.of("Azul"), 25.06);
                String json = new ObjectMapper().writeValueAsString(response);
                Product product = ProductFactory.getInstance(response);

                BDDMockito.given(productService.update(Mockito.anyString(), Mockito.any(CreateProductDTO.class)))
                                .willReturn(product);

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/v1/products/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request)
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("id", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("title", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("description", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("colors", Matchers.notNullValue()));

        }

        @Test
        @DisplayName("PUT /api/v1/products -> should throw an error when attempting to update a product with a null values")
        public void shouldThrowErrorWhenUpdatingProductWithNullValues() throws Exception {
                var id = "687fe480e52d11d99eba49b1";
                CreateProductDTO data = new CreateProductDTO("", "", null, null);
                String json = new ObjectMapper().writeValueAsString(data);

                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .put("/api/v1/products/".concat(id))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("path", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("errors").exists());

        }

        @Test
        @DisplayName("PUT /api/v1/products -> should throw an error when attempting to update a product with a duplicated title")
        public void shouldThrowErrorWhenUpdatingProductWithDuplicatedTitle() throws Exception {
                var id = "687fe480e52d11d99eba49b1";
                CreateProductDTO data = new CreateProductDTO("test2", "test2", List.of("Azul"), 25.50);
                String json = new ObjectMapper().writeValueAsString(data);

                BDDMockito.given(productService.update(id, data)).willThrow(new DuplicatedTitleException(
                                String.format("Product with this title %s already exists.", data.title())));
                MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                                .put("/api/v1/products/".concat(id))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json);

                mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andExpect(MockMvcResultMatchers.jsonPath("path", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("message", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.any(String.class)))
                                .andExpect(MockMvcResultMatchers.jsonPath("errors").exists());

        }

}
