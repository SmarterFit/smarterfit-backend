package com.smarterfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ClassGroupRequestDTO(

        @NotBlank String name,
        @NotNull Integer capacity,
        @NotNull Integer totalPresent,
        @NotNull @Size(min = 1) List<UUID> planModalityIds
) {
}
