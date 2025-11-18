package com.tpintegrador.adapter.api.dto;

import java.time.LocalDate;

public record CreateProjectRequestDTO(
        String name,
        LocalDate startDate,
        LocalDate endDate
) {
}
