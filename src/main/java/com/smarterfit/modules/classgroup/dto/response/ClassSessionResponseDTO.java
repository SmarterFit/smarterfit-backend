package com.smarterfit.modules.classgroup.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.BookingStatus;

import lombok.Builder;

@Builder(toBuilder = true)
public record ClassSessionResponseDTO(
        UUID classSessionId,
        UUID classGroupId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        BookingStatus bookingStatus
){}
