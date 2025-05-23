package com.smarterfit.modules.training.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutExercisesResponseDTO {
    private UUID id;
    private UUID workoutDayId;
    private UUID exerciseId;
    private String exerciseName;
    private String muscleGroup;
    private Integer sets;
    private String reps;
}
