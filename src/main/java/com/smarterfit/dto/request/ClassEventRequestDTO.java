package com.smarterfit.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClassEventRequestDTO(
        @NotNull(message = "Class group ID is required.")
        UUID classGroupId,

        @Min(value = 1, message = "Capacity must be greater than 0.")
        @NotNull(message = "Capacity is required.")
        Integer capacity,

        @NotNull(message = "Start date is required")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime startDate,

        @NotNull(message = "End date is required")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime endDate
) {
}
