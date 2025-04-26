package com.smarterfit.dto.request.training_group;

import java.time.LocalDate;

import com.smarterfit.enums.GroupType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDTO(
      @NotNull(message = "Group name cannot be null")
      @NotBlank(message = "Group name cannot be blank")
      @Size(min = 3, max = 100, message = "Group name must be between 3 and 100 characters") String name,

      @NotNull(message = "Group type cannot be null") GroupType groupType,

      LocalDate startDate,
      LocalDate endDate) {

}
