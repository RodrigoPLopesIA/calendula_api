package br.com.rodrigoplopesdev.calendula_api.models;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class Product {

    private UUID id;
    private String title;
    private String description;
    private Double price;
    private List<String> colors;

}
