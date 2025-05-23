package com.smarterfit.modules.training.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutExerciseRequestDTO {

    @NotNull(message = "Workout day ID is required")
    private UUID workoutDayId;

    @NotNull(message = "Exercise ID is required")
    private UUID exerciseId;

    @NotNull(message = "Sets are required")
    @Min(1)
    private Integer sets;

    @NotBlank(message = "Reps are required")
    @Pattern(
            regexp = "^\\d{1,2}(-\\d{1,2})?$",
            message = "Reps must be a number or a range (e.g., 12 or 12-15)"
    )
    private String reps;

}
