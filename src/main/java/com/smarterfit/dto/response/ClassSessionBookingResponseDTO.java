package com.smarterfit.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClassSessionBookingResponseDTO(

        UUID id,
        UUID userId,
        UUID classSessionId,
        LocalDateTime bookingDate,
        String bookingStatus

) {
}
