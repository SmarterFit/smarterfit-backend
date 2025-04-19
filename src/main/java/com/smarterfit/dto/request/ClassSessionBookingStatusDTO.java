package com.smarterfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClassSessionBookingStatusDTO(
        @NotNull(message = "User ID is required")
        UUID userId,

        @NotNull(message = "Class group ID is required")
        UUID classSessionId,

        @NotBlank(message = "Booking time is required")
        String bookingStatus

) {
}
