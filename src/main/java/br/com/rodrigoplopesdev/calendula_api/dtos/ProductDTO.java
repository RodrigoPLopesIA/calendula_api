package br.com.rodrigoplopesdev.calendula_api.dtos;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ProductDTO(UUID id, String title, String description, Double price, String size, LocalDate createdAt, LocalDate updatedAt) {
}
