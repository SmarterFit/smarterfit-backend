package com.smarterfit.modules.training.dto.response.workoutplan;

import com.smarterfit.modules.training.dto.response.workoutday.WorkoutDayAllExerciseResponseDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutPlanExerciseResponseDTO {

    private UUID planId;

    private List<WorkoutDayAllExerciseResponseDTO> days;

    private String title;

    public void addDays(WorkoutDayAllExerciseResponseDTO dayDTO){
      days.add(dayDTO);
    }

}
