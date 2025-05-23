package com.smarterfit.modules.training.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExerciseResponseDTO {
    private UUID id;
    private String name;
    private String muscleGroup;

}
