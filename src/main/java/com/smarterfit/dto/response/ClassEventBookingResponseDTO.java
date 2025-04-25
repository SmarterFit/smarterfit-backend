package com.smarterfit.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClassEventBookingResponseDTO(

        UUID id,
        UUID classEventId,
        LocalDateTime bookingDate,
        String bookingStatus

) {
}
