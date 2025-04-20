package com.smarterfit.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ClassSessionRequestDTO(
        @NotNull(message = "Class group ID is required.")
        UUID classGroupId,

        @NotNull(message = "Start time is required.")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime startTime,

        @NotNull(message = "End time is required.")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime endTime,

        @NotBlank(message = "Booking status is required.")
        String bookingStatus,

        @Min(value = 1, message = "Minimum capacity must be 1.")
        @Max(value = 50, message = "Maximum capacity must be 50.")
        Integer capacity
){}
