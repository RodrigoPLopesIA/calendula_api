package br.com.rodrigoplopesdev.calendula_api.dtos;

import lombok.Builder;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Builder
public record CreateProductDTO(@NotBlank String title, @NotBlank  String description, @NotEmpty @Size(min = 1) List<String> colors, @NotNull Double price) {
}
