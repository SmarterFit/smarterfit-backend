package com.smarterfit.modules.traininggroup.dto.request;

import java.util.List;
import java.util.UUID;

import com.smarterfit.common.enums.TrainingGroupType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchTrainingGroupRequestDTO(
      String nameTerm,
      UUID userId,
      List<TrainingGroupType> types,
      Boolean includeEnded,
      Boolean includeNotStarted) {
}
