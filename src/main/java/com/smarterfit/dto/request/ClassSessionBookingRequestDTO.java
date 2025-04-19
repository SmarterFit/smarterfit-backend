package com.smarterfit.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record ClassSessionBookingRequestDTO(
        @NotNull(message = "User ID (creator) is required")
        UUID userId,

        @NotNull(message = "Class group ID is required")
        UUID classSessionId,

        @NotNull(message = "Booking date is required")
        LocalDateTime bookingDate,

        @NotBlank(message = "Booking time is required")
        String bookingStatus

) {
}
