package com.tpintegrador.adapter.api.dto;

import com.tpintegrador.domain.model.ProjectStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeProjectStatusRequestDTO(
        @NotNull
        ProjectStatus newStatus
) {
}
