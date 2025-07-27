package br.com.rodrigoplopesdev.calendula_api.controllers;

import br.com.rodrigoplopesdev.calendula_api.config.SecurityConfig;
import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;
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

        CreateUserDTO createUserDTO = new CreateUserDTO("Test", "Test", "test@email.com", "123456", "123456");
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
}