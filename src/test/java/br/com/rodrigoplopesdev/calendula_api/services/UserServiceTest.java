package br.com.rodrigoplopesdev.calendula_api.services;


import br.com.rodrigoplopesdev.calendula_api.dtos.AddressDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.CreateUserDTO;
import br.com.rodrigoplopesdev.calendula_api.dtos.ListUserDTO;
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

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @InjectMocks
    private UserService userService;


    @Mock
    private UserRepository userRepository;



    @Test
    @DisplayName("Should create user")
    public void shouldCreateUser(){

        CreateUserDTO createDto = new CreateUserDTO("test", "test", "test@email.com", "123456", "123456");
        ListUserDTO listDTO = new ListUserDTO("123456","test", "test", "test@email.com", "123456");

        User user = new User("123456", "test", "test", "test@email.com", "123456");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        var result = userService.create(createDto);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.id()).isEqualTo(listDTO.id());

        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

}
