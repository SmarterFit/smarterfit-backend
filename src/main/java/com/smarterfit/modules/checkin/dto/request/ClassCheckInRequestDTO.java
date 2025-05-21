package com.smarterfit.modules.checkin.dto.request;

import java.util.UUID;

import com.smarterfit.common.enums.CheckInStatus;
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
public class ClassCheckInRequestDTO {
   @NotNull(message = "userId cannot be null")
   private UUID userId;

   @NotNull(message = "classSessionId cannot be null")
   private UUID classSessionId;

   @NotNull(message = "Status cannot be null")
   private CheckInStatus status;
}
