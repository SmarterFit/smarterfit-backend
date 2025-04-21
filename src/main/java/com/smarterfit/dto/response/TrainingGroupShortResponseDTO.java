package com.smarterfit.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.smarterfit.enums.GroupType;

public record TrainingGroupShortResponseDTO(
      UUID id,
      String name,
      GroupType groupType,
      LocalDate startDate,
      LocalDate endDate) {

}
