package br.com.rodrigoplopesdev.calendula_api.services;


import br.com.rodrigoplopesdev.calendula_api.repositories.UserRepository;
import org.assertj.core.api.Assertions;
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
    @DisplayName("Should create user")
    public void shouldCreateUser(){



        var result = userService.create();

        Assertions.assertThat(result).isNotNull();
    }

}
