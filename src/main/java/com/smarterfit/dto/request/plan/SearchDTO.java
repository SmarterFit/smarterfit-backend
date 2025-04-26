package com.smarterfit.dto.request.plan;

public record SearchDTO(
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
