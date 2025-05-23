package com.smarterfit.modules.training.dto.request;

import com.smarterfit.common.enums.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutDayRequestDTO {

    @NotNull(message = "Workout plan ID is required")
    private UUID workoutPlanId;

    @NotBlank(message = "Day of week is required")
    private DayOfWeek dayOfWeek;

    @NotBlank(message = "Muscle group is required")
    private MuscleGroup muscleGroup;

}
