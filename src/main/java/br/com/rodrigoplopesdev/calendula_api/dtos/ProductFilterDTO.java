package br.com.rodrigoplopesdev.calendula_api.dtos;

import java.math.BigDecimal;
import java.util.List;

public record ProductFilterDTO(
    String search,
    String category,
    List<String> colors,
    BigDecimal minPrice,
    BigDecimal maxPrice
) {}

