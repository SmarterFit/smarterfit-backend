package com.smarterfit.modules.classgroup.dto.request.classevent.booking;

import com.smarterfit.common.enums.AttendanceStatus;
import com.smarterfit.common.enums.BookingStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateClassEventBookingRequestDTO {
    @NotNull(message = "User ID  is required")
    private UUID userId;

    @NotNull(message = "Class group ID is required")
    private UUID classEventId;

    @NotNull(message = "Booking date is required")
    private LocalDateTime bookingDate;

    @NotBlank(message = "Booking time is required")
    private BookingStatus bookingStatus;

    private AttendanceStatus attendanceStatus;
}
