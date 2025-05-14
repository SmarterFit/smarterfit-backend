package com.smarterfit.modules.billing.dto.response.plan;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreatedPlanResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private Integer maxUsers;
    private Integer maxClasses;
    private LocalDateTime deletedAt;
}
