package com.smarterfit.modules.classgroup.dto.request.classevent;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record CreateClassEventRequestDTO(
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
