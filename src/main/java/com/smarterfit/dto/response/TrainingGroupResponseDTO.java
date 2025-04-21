package com.smarterfit.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.smarterfit.enums.GroupType;

public record TrainingGroupResponseDTO(
   UUID id,
   String name,
   GroupType groupType,
   Set<TrainingGroupUserResponseDTO> participants,
   LocalDate startDate,
   LocalDate endDate
) {

}
