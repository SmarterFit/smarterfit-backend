package com.smarterfit.modules.training.dto.response.workoutplan;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkoutPlanResponseDTO {
    private UUID id;
    private UUID userId;
    private String title;
    private LocalDateTime createdAt;
}
