package com.smarterfit.modules.checkin.dto.request;

import java.time.LocalDateTime;

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
public class FilterPresenceSnapshotRequestDTO {
   @NotNull(message = "startDate cannot be null")
   private LocalDateTime startDate;
   @NotNull(message = "endDate cannot be null")
   private LocalDateTime endDate;
}
