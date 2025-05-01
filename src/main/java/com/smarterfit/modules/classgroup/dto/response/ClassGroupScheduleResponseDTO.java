package com.smarterfit.modules.classgroup.dto.response;

import java.time.LocalTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClassGroupScheduleResponseDTO {
    private UUID id;
    private UUID classGroupId;
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
