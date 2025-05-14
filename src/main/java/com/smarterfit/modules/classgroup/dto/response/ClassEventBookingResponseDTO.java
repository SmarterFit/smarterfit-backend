package com.smarterfit.modules.classgroup.dto.response;

import java.util.UUID;

import com.smarterfit.common.enums.BookingStatus;

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
public class ClassEventBookingResponseDTO {
   private UUID userId;
   private UUID classEventId;
   private BookingStatus bookingStatus;
}
