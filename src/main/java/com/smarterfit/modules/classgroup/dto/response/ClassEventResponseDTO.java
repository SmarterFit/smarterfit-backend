package com.smarterfit.modules.classgroup.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.EventStatus;

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
public class ClassEventResponseDTO {
    private UUID id;
    private UUID classGroupId;
    private Integer capacity;
    private Integer attendanceCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private EventStatus status;
}
