package br.com.rodrigoplopesdev.calendula_api.controllers;

import br.com.rodrigoplopesdev.calendula_api.config.SecurityConfig;
import br.com.rodrigoplopesdev.calendula_api.dtos.AuthDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;
import br.com.rodrigoplopesdev.calendula_api.exceptions.BusinessException;
import br.com.rodrigoplopesdev.calendula_api.services.ProductService;
import br.com.rodrigoplopesdev.calendula_api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private UserService userService;


    private String API_ROUTE = "/api/v1/users";

    @Test
    @DisplayName("should create a new user")
    public void shouldCreateNewUser() throws Exception{

        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Test", "test@email.com", "12345678", "12345678");
        var json = new ObjectMapper().writeValueAsString(createUserDTO);
        ListUserDTO listUserDTO = new ListUserDTO("6886474ad5e12aca82409ea5", "Test", "Test", "test@email.com");

        BDDMockito.given(userService.create(Mockito.any(CreateUserDTO.class))).willReturn(listUserDTO);

        var result = post(API_ROUTE.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("email", Matchers.any(String.class)))
                .andExpect(jsonPath("firstName", Matchers.any(String.class)))
                .andExpect(jsonPath("lastName", Matchers.any(String.class)))
                .andExpect(jsonPath("email", Matchers.any(String.class)));
    }

    @Test
    @DisplayName("should return business excepiton when try to ceate a exists user")
    public void shouldReturnBusinesException() throws Exception{

        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Test", "test@email.com", "12345678", "12345678");
        var json = new ObjectMapper().writeValueAsString(createUserDTO);
        ListUserDTO listUserDTO = new ListUserDTO("6886474ad5e12aca82409ea5", "Test", "Test", "test@email.com");

        BDDMockito.given(userService.create(Mockito.any(CreateUserDTO.class))).willThrow(new BusinessException("User already exists!"));

        var result = post(API_ROUTE.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(result)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User already exists!"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }
    @Test
    @DisplayName("should return business exception when send password minus than 8")
    public void shouldReturnArgumentExceptionInvalidPasswordSize() throws Exception{

        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Test", "test@email.com", "12345", "12345");
        var json = new ObjectMapper().writeValueAsString(createUserDTO);
        ListUserDTO listUserDTO = new ListUserDTO("6886474ad5e12aca82409ea5", "Test", "Test", "test@email.com");

        var result = post(API_ROUTE.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(result)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Arguments invalid!"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errors.password").value("size must be between 8 and 16"))
                .andExpect(jsonPath("$.errors.confirmPassword").value("size must be between 8 and 16"));

        BDDMockito.verify(userService, Mockito.never()).create(Mockito.any(CreateUserDTO.class));
    }

    @Test
    @DisplayName("should return business excepiton when try to send null values")
    public void shouldReturnArgumentExceptionInvalidWhenSendNullValues() throws Exception{

        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Test", "", "", "");
        var json = new ObjectMapper().writeValueAsString(createUserDTO);
        ListUserDTO listUserDTO = new ListUserDTO("6886474ad5e12aca82409ea5", "Test", "Test", "test@email.com");

        var result = post(API_ROUTE.concat("/register"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(result)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Arguments invalid!"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errors.email").value("must not be blank"));

        BDDMockito.verify(userService, Mockito.never()).create(Mockito.any(CreateUserDTO.class));
    }

    @Test
    @DisplayName("Should authenticate user")
    public void shouldAuthenticateUser() throws Exception {

        var data = new AuthDTO("test@email.com", "12345678");
        var json = new ObjectMapper().writeValueAsString(data);
        var result = post(API_ROUTE.concat("/login"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);
        mvc.perform(result).andExpect(status().isOk());
    }
}