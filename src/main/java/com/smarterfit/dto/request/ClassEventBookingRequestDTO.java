package com.smarterfit.dto.request;

import com.smarterfit.enums.AttendanceStatus;
import com.smarterfit.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClassEventBookingRequestDTO(
        @NotNull(message = "User ID  is required")
        UUID userId,

        @NotNull(message = "Class group ID is required")
        UUID classEventId,

        @NotNull(message = "Booking date is required")
        LocalDateTime bookingDate,

        @NotBlank(message = "Booking time is required")
        Status bookingStatus,

        AttendanceStatus attendanceStatus
) {
}
