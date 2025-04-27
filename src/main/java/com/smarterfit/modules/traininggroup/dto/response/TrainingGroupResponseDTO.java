package com.smarterfit.modules.traininggroup.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.smarterfit.common.enums.TrainingGroupType;
import lombok.Builder;

@Builder(toBuilder = true)
public record TrainingGroupResponseDTO(
            UUID id,
            String name,
            TrainingGroupType type,
            LocalDate startDate,
            LocalDate endDate) {

}
