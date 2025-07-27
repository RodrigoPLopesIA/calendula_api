package br.com.rodrigoplopesdev.calendula_api.models;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class Address {
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    private String country;

}
