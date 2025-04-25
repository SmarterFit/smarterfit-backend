package com.smarterfit.dto.request;

import com.smarterfit.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ClassEventBookingStatusDTO(
        @NotNull(message = "User ID is required")
        UUID userId,

        @NotNull(message = "Class group ID is required")
        UUID classEventId,

        @NotBlank(message = "Booking time is required")
        Status bookingStatus

) {
}
