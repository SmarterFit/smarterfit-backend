package com.smarterfit.modules.traininggroup.dto.request;

import java.time.LocalDate;

import com.smarterfit.common.enums.TrainingGroupType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder(toBuilder = true)
public record UpdateTrainingGroupRequestDTO(
      @NotNull(message = "Group name cannot be null")
      @NotBlank(message = "Group name cannot be blank")
      @Size(min = 3, max = 100, message = "Group name must be between 3 and 100 characters") String name,

      @NotNull(message = "Group type cannot be null") 
      TrainingGroupType type,

      LocalDate startDate,
      LocalDate endDate) {

}
