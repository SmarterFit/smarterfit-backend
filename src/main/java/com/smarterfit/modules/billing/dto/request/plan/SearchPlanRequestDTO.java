package com.smarterfit.modules.billing.dto.request.plan;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchPlanRequestDTO(
            String nameTerm,
            Double minPrice,
            Double maxPrice,
            Integer minDuration,
            Integer maxDuration,
            Integer minMaxUsers,
            Integer maxMaxUsers,
            Integer minMaxClasses,
            Integer maxMaxClasses,
            Boolean includeDeleted) {

}
