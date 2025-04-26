package com.smarterfit.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PlanResponseDTO(
      UUID id,
      String name,
      String description,
      Double price,
      Integer duration,
      Integer maxUsers,
      Integer maxClasses,
      LocalDateTime deletedAt) {

}
