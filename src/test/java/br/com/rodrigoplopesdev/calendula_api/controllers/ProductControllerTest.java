package br.com.rodrigoplopesdev.calendula_api.controllers;

import br.com.rodrigoplopesdev.calendula_api.dtos.CreateProductDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ProductDTO;
import br.com.rodrigoplopesdev.calendula_api.exceptions.BusinessException;
import br.com.rodrigoplopesdev.calendula_api.models.Product;
import br.com.rodrigoplopesdev.calendula_api.patterns.factory.ProductFactory;
import br.com.rodrigoplopesdev.calendula_api.services.ProductService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.UUID;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Matches;
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
}
