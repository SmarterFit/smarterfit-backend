package com.smarterfit.modules.training.dto.response.workoutday;

import com.smarterfit.modules.training.dto.response.WorkoutExerciseResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutDayAllExerciseResponseDTO {

    List<WorkoutExerciseResponseDTO> exercises;
}
