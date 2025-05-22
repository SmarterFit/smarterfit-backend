package com.smarterfit.modules.training.dto.response;

import com.smarterfit.common.enums.ExperienceLevel;
import com.smarterfit.common.enums.Goal;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrainingGoalResponseDTO {
    UUID id;
    private Goal goal;
    private ExperienceLevel experienceLevel;
    private Integer weeklyFrequency;
}
