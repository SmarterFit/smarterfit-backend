package com.smarterfit.dto.response.training_group;

import java.time.LocalDate;
import java.util.UUID;

import com.smarterfit.enums.GroupType;

public record TrainingGroupResponseDTO(
            UUID id,
            String name,
            GroupType groupType,
            LocalDate startDate,
            LocalDate endDate) {

}
