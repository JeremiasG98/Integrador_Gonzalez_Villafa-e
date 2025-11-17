package com.tpintegrador.adapter.api.dto;

import com.tpintegrador.domain.model.TaskStatus;

public record TaskResponseDTO(
        Long id,
        String title,
        int estimateHours,
        String assignee,
        TaskStatus status,
        Long projectId
) {
}
