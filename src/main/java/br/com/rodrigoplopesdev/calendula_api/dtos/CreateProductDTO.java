package br.com.rodrigoplopesdev.calendula_api.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateProductDTO(String title, String description, List<String> colors, Double price) {
}
