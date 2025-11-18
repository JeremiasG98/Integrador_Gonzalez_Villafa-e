package com.tpintegrador.adapter.api.dto;

import com.tpintegrador.domain.model.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeTaksStatusRequestDTO(
        @NotNull
        TaskStatus newStatus
) {
}
