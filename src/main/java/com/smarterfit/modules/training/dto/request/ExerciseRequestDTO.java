package com.smarterfit.modules.training.dto.request;

import com.smarterfit.common.enums.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExerciseRequestDTO {

    @NotBlank(message = "Exercise name cannot be blank")
    private String name;

    @NotBlank(message = "Muscle group is required")
    private MuscleGroup muscleGroup;
}
