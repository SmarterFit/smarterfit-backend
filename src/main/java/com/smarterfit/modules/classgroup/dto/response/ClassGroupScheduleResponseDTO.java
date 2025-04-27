package com.smarterfit.modules.classgroup.dto.response;

import java.time.LocalTime;
import java.util.UUID;

import lombok.Builder;

@Builder(toBuilder = true)
public record   ClassGroupScheduleResponseDTO(
        UUID id,
        UUID classGroupId,
        Integer dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {
}
