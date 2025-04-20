package com.smarterfit.dto.response;

import java.util.Set;
import java.util.UUID;

public record   ClassGroupScheduleResponseDTO(
        UUID id,
        UUID classGroupId,
        Integer dayOfWeek,
        String startTime,
        String endTime

) {
}
