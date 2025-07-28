package br.com.rodrigoplopesdev.calendula_api.services;


import br.com.rodrigoplopesdev.calendula_api.dtos.AddressDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;
import br.com.rodrigoplopesdev.calendula_api.exceptions.BusinessException;
import br.com.rodrigoplopesdev.calendula_api.models.User;
import br.com.rodrigoplopesdev.calendula_api.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @InjectMocks
    private UserService userService;


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetails userDetails;

    CreateUserDTO createDto;
    ListUserDTO listDTO;

    User listUser;
    User createUser;

    @BeforeEach
    public void setup(){
        createDto = new CreateUserDTO("test", "test", "test@email.com", "123456", "123456");
        listDTO = new ListUserDTO("123456","test", "test", "test@email.com");

        listUser = new User("123456", "test", "test", "test@email.com", "123456");
        createUser = new User("test", "test", "test@email.com", "123456");

    }


    @Test
    @DisplayName("Should create user")
    public void shouldCreateUser(){

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(listUser);
        Mockito.when(userRepository.existsByEmail(createDto.email())).thenReturn(false);

        var result = userService.create(createDto);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.id()).isEqualTo(listDTO.id());

        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Should return business exception when create user already exists")
    public void shouldReturnBusinessExceptionWhenCreateUserAlreadyExists(){

        Mockito.when(userRepository.existsByEmail(createDto.email())).thenReturn(true);

        var result = Assertions.catchThrowable(() -> userService.create(createDto));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isInstanceOf(BusinessException.class);
        Assertions.assertThat(result.getMessage()).isEqualTo("User already exists!");


        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Should find user by email")
    public void shouldFindUserByEmail(){
        String username = "test@email.com";

        Mockito.when(userRepository.findByEmail(username)).thenReturn(userDetails);

        var result = userService.loadUserByUsername(username);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found")
    public void shouldReturnUsernameNotFoundException() {
        String username = "test@email.com";

        Mockito.when(userRepository.findByEmail(username)).thenReturn(null);

        Throwable result = Assertions.catchThrowable(() -> userService.loadUserByUsername(username));

        Assertions.assertThat(result)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found");
    }

}
