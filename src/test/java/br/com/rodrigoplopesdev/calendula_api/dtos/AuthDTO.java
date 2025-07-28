package br.com.rodrigoplopesdev.calendula_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthDTO(@NotBlank @Email() String email, @NotBlank @Size(min = 8, max = 16) String password) {
}
