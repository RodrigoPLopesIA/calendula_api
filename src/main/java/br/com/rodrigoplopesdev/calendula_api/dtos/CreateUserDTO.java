package br.com.rodrigoplopesdev.calendula_api.dtos;

import jakarta.validation.constraints.*;

public record CreateUserDTO(String firstName, String lastName, @NotBlank() @Email() String email, @NotBlank() @Size(min = 8, max = 16) String password, @NotBlank() @Size(min = 8, max = 16) String confirmPassword) {


}
