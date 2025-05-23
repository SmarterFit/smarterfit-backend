package com.smarterfit.modules.training.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutExercisesRequestDTO {

    @NotNull(message = "Workout day ID is required")
    private UUID workoutDayId;

    @NotNull(message = "Exercise ID is required")
    private UUID exerciseId;

    @NotNull(message = "Sets are required")
    @Min(1)
    private Integer sets;

    @NotBlank(message = "Reps are required")
    private String reps;

}
