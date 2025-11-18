package com.tpintegrador.adapter.api.dto;

import com.tpintegrador.domain.model.ProjectStatus;

import java.time.LocalDate;

public record ProjectResponseDTO(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        ProjectStatus status
) {
}
