package com.smarterfit.modules.classgroup.dto.response;

import java.util.UUID;

import com.smarterfit.common.enums.BookingStatus;

import lombok.Builder;

@Builder(toBuilder = true)
public record ClassEventBookingResponseDTO(

                UUID id,
                UUID classEventId,
                BookingStatus bookingStatus) {
}
