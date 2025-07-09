package br.com.rodrigoplopesdev.calendula_api.models;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String title;
    private String description;
    private Double price;
    private List<String> colors;

}
