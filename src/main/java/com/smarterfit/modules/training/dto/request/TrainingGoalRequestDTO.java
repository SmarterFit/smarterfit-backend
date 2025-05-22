package com.smarterfit.modules.training.dto.request;

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
public class TrainingGoalRequestDTO {

    @NotNull(message = "Goal cannot be null")
    private Goal goal;

    @NotNull(message = "Experience level cannot be null")
    private ExperienceLevel experienceLevel;

    @NotNull(message = "Weekly frequency cannot be null")
    @Min(value = 1, message = "Frequency must be at least 1")
    @Max(value = 7, message = "Frequency cannot exceed 7")
    private Integer weeklyFrequency;
}
