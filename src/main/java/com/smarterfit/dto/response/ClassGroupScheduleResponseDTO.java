package com.smarterfit.dto.response;

import java.util.Set;
import java.util.UUID;

public record   ClassGroupScheduleResponseDTO(
        UUID id,
        UUID classGroupId,
        Set<Integer> daysOfWeek,
        String startTime,
        String endTime

) {
}
