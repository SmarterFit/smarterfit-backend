package com.smarterfit.modules.training.dto.request.workoutplans;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutPlanRequestDTO {

    @NotNull(message = "Training Goal ID is required")
    private UUID trainingGoalId;

    @NotBlank(message = "Title is required")
    private String title;
}
