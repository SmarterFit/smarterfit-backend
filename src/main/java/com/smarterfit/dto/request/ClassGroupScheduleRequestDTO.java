package com.smarterfit.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;



import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;


public record ClassGroupScheduleRequestDTO(
        @NotNull(message = "Class group ID is required")
        UUID classGroupId,

        /**
         * Days of the week when this class occurs.
         * Valid values: 2 = Monday, ..., 8 = Sunday.
         */
        @Min(value = 2, message = "Day of the week must be between 2 (Monday) and 8 (Sunday)")
        @Max(value = 8, message = "Day of the week must be between 2 (Monday) and 8 (Sunday)")
        Integer dayOfWeek,

        /**
         * Start time in 24-hour HH:mm format (e.g., 08:00, 14:30).
         */
        @NotNull(message = "Start time is required")
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,

        /**
         * End time in 24-hour HH:mm format (e.g., 09:00, 15:30).
         */
        @NotNull(message = "End time is required")
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime

) {

}
