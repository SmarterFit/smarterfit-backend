package com.smarterfit.dto.response;

import java.util.UUID;

public record PlanResponseDTO(
   UUID id,
   String name,
   String description,
   double price,
   int duration,
   int maxUsers
) {

}
