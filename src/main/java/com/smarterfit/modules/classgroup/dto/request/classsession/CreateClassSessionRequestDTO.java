package com.smarterfit.modules.classgroup.dto.request.classsession;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.common.enums.SessionStatus;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record CreateClassSessionRequestDTO(
            @NotNull(message = "Class group ID is required.") UUID classGroupId,

            @NotNull(message = "Start time is required.") @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime startTime,

            @NotNull(message = "End time is required.") @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime endTime,

            @NotBlank(message = "Status is required.") SessionStatus status,

            @Min(value = 1, message = "Minimum capacity must be 1.") @Max(value = 50, message = "Maximum capacity must be 50.") Integer capacity) {
}
