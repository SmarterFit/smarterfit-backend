package com.smarterfit.modules.training.dto.request.workoutplans;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutPlanExercisesRequestDTO {
    private UUID planId;
}
