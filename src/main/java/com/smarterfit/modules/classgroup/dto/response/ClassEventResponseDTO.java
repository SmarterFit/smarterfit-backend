package com.smarterfit.modules.classgroup.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.EventStatus;

import lombok.Builder;

@Builder(toBuilder = true)
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
