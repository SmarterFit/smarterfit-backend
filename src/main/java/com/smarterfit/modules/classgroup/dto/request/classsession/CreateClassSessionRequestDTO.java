package com.smarterfit.modules.classgroup.dto.request.classsession;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.common.enums.SessionStatus;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateClassSessionRequestDTO {

    @NotNull(message = "Class group ID is required.")
    private UUID classGroupId;

    private String description;

    @NotNull(message = "Start time is required.")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required.")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endTime;

    private SessionStatus status;

    @Min(value = 1, message = "Minimum capacity must be 1.")
    @Max(value = 50, message = "Maximum capacity must be 50.")
    private Integer capacity;
}
