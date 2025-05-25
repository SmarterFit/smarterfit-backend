package com.smarterfit.modules.checkin.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GymCheckInAndCheckOutRequestDTO {
   @NotNull(message = "userId cannot be null")
   private UUID userId;
}
