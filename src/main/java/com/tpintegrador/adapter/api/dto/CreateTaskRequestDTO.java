package com.tpintegrador.adapter.api.dto;

import com.tpintegrador.domain.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTaskRequestDTO(
        @NotBlank
        String title,

        @NotNull
        @Positive
        Integer estimateHours,

        String assignee,

        @NotNull
        TaskStatus status
) {
}
