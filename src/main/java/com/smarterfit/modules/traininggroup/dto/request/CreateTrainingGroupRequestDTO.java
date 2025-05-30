package com.smarterfit.modules.traininggroup.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.TrainingGroupType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateTrainingGroupRequestDTO {
   @NotNull(message = "Group name cannot be null")
   @NotBlank(message = "Group name cannot be blank")
   @Size(min = 3, max = 100, message = "Group name must be between 3 and 100 characters")
   private String name;

   @NotNull(message = "Group type cannot be null")
   private TrainingGroupType type;

   @NotNull(message = "Owner ID cannot be null")
   private UUID ownerId;

   private LocalDateTime startDate;
   private LocalDateTime endDate;
}
