package com.smarterfit.modules.checkin.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

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
public class GymCheckInResponseDTO {
   private UUID id;
   private UserResponseDTO user;
   private LocalDateTime checkInTime;
   private LocalDateTime checkOutTime;
}
