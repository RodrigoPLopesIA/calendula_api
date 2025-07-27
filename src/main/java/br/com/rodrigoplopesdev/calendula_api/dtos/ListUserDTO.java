package br.com.rodrigoplopesdev.calendula_api.dtos;

public record ListUserDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        String password,
        AddressDTO address
) {
}
