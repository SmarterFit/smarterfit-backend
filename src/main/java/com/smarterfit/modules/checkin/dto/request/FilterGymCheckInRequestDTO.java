package com.smarterfit.modules.checkin.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
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
public class FilterGymCheckInRequestDTO {
   @NotNull(message = "userId cannot be null")
   private UUID userId;
   @NotNull(message = "startDate cannot be null")
   private LocalDateTime startDate;
   @NotNull(message = "endDate cannot be null")
   private LocalDateTime endDate;
}
