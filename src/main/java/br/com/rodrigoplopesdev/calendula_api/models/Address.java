package br.com.rodrigoplopesdev.calendula_api.models;


import br.com.rodrigoplopesdev.calendula_api.dtos.AddressDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public Address(AddressDTO address) {
        this.setStreet(address.street());
        this.setNumber(address.number());
        this.setCity(address.city());
        this.setState(address.state());
        this.setZipCode(address.zipCode());
        this.setCountry(address.country());
    }
}
