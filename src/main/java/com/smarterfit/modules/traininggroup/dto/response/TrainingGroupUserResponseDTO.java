package com.smarterfit.modules.traininggroup.dto.response;

import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import lombok.Builder;

@Builder(toBuilder = true)
public record TrainingGroupUserResponseDTO(
      UserResponseDTO user,
      Boolean isAdmin,
      Integer points) {
}
