package br.com.rodrigoplopesdev.calendula_api.services;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @InjectMocks
    private UserService userService;


    @Mock
    private UserRepository userRepository;


    @Test
    @DisplayName("Should authenticate user")
    public void shouldAuthenticateUser(){

    }

}
