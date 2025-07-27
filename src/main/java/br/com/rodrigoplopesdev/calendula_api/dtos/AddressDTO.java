package br.com.rodrigoplopesdev.calendula_api.dtos;

import br.com.rodrigoplopesdev.calendula_api.models.Address;

public record AddressDTO(
        String street,
        String number,
        String city,
        String state,
        String zipCode,
        String country
) {

    public AddressDTO(Address address) {
        this(address.getStreet(), address.getNumber(), address.getCity(), address.getState(), address.getZipCode(), address.getCountry());
    }
}
