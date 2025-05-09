package com.smarterfit.modules.classgroup.dto.request.classgroup.schedule;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateClassGroupScheduleRequestDTO {

    @NotNull(message = "Class group ID is required")
    private UUID classGroupId;

    /**
     * Days of the week when this class occurs.
     */
    @NotNull(message = "Day of the week is required")
    private DayOfWeek dayOfWeek;

    /**
     * Start time in 24-hour HH:mm format (e.g., 08:00, 14:30).
     */
    @NotNull(message = "Start time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    /**
     * End time in 24-hour HH:mm format (e.g., 09:00, 15:30).
     */
    @NotNull(message = "End time is required")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
}
