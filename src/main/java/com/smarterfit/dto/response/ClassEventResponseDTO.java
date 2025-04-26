package com.smarterfit.dto.response;

import com.smarterfit.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClassEventResponseDTO(

         UUID id,
         UUID classGroupId,
         Integer capacity,
         Integer attendanceCount,
         LocalDateTime startDate,
         LocalDateTime endDate,
         EventStatus status
) {
}
