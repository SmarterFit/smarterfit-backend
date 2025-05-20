package com.smarterfit.modules.checkin.dto.response;

import java.time.LocalDateTime;

import com.smarterfit.common.enums.CheckInStatus;
import com.smarterfit.modules.classgroup.dto.response.ClassSessionResponseDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

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
public class ClassCheckInResponseDTO {
   private UserResponseDTO user;
   private ClassSessionResponseDTO classSession;
   private LocalDateTime checkInTime;
   private CheckInStatus status;
}
