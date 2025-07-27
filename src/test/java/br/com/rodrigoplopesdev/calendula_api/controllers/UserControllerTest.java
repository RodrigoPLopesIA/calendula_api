package br.com.rodrigoplopesdev.calendula_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
}