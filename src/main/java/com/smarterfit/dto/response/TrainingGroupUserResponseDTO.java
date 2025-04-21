package com.smarterfit.dto.response;

import java.util.Set;

public record TrainingGroupUserResponseDTO(
      UserShortResponseDTO user,
      Boolean isAdmin,
      Integer points) {
}
