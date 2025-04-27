package com.smarterfit.modules.classgroup.dto.request.classevent.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

import com.smarterfit.common.enums.BookingStatus;

@Builder(toBuilder = true)
public record UpdateClassEventBookingRequestDTO(
        @NotNull(message = "User ID is required")
        UUID userId,

        @NotNull(message = "Class group ID is required")
        UUID classEventId,

        @NotBlank(message = "Booking status is required")
        BookingStatus bookingStatus
) {
}
