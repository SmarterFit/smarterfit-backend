package com.smarterfit.modules.training.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutDayResponseDTO {
    private UUID id;
    private UUID workoutPlanId;
    private String dayOfWeek;
    private String muscleGroup;
}
