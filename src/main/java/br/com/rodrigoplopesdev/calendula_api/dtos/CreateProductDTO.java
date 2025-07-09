package br.com.rodrigoplopesdev.calendula_api.dtos;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CreateProductDTO(String title, String description, List<String> cores, Double price) {
}
