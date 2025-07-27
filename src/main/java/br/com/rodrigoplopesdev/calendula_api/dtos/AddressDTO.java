package br.com.rodrigoplopesdev.calendula_api.dtos;

public record AddressDTO(
        String street,
        String number,
        String city,
        String state,
        String zipCode,
        String country
) {
}
