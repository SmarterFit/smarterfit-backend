package com.smarterfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ClassSessionRequestDTO(
       @NotNull UUID classGroupId,
       @NotNull LocalDateTime startTime,
       @NotNull LocalDateTime endTime,
       @NotBlank String BookingStatus
){}
