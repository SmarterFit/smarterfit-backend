package com.smarterfit.modules.traininggroup.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.smarterfit.common.enums.TrainingGroupType;

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
public class TrainingGroupResponseDTO {
   private UUID id;
   private String name;
   private TrainingGroupType type;
   private LocalDate startDate;
   private LocalDate endDate;
}
