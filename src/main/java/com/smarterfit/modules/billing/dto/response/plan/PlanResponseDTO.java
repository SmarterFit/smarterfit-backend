package com.smarterfit.modules.billing.dto.response.plan;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PlanResponseDTO {
    private UUID id;
    private String name;
}
