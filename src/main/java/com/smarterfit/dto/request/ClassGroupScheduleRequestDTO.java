package com.smarterfit.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.validator.WeekdaysValid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;


public record ClassGroupScheduleRequestDTO(
        @NotNull(message = "Class group ID is required")
        UUID classGroupId,

        /**
         * Days of the week when this class occurs.
         * Valid values: 2 = Monday, ..., 8 = Sunday.
         * At least one day must be selected.
         */
        @WeekdaysValid
        Set<Integer> daysOfWeek,

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
