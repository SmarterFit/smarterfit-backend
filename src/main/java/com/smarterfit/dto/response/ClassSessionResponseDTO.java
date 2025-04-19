package com.smarterfit.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClassSessionResponseDTO(
        UUID classSessionId,
        UUID classGroupId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String BookingStatus
){}
