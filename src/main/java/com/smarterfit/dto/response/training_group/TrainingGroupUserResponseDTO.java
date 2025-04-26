package com.smarterfit.dto.response.training_group;

import com.smarterfit.dto.response.UserResponseDTO;

public record TrainingGroupUserResponseDTO(
      UserResponseDTO user,
      Boolean isAdmin,
      Integer points) {
}
