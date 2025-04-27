package com.smarterfit.modules.classgroup.dto.request.classevent.booking;

import com.smarterfit.common.enums.AttendanceStatus;
import com.smarterfit.common.enums.BookingStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record CreateClassEventBookingRequestDTO(
        @NotNull(message = "User ID  is required")
        UUID userId,

        @NotNull(message = "Class group ID is required")
        UUID classEventId,

        @NotNull(message = "Booking date is required")
        LocalDateTime bookingDate,

        @NotBlank(message = "Booking time is required")
        BookingStatus bookingStatus,

        AttendanceStatus attendanceStatus
) {
}
