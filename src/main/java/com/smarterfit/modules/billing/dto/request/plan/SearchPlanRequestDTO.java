package com.smarterfit.modules.billing.dto.request.plan;

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
public class SearchPlanRequestDTO {
   private String nameTerm;
   private Double minPrice;
   private Double maxPrice;
   private Integer minDuration;
   private Integer maxDuration;
   private Integer minMaxUsers;
   private Integer maxMaxUsers;
   private Integer minMaxClasses;
   private Integer maxMaxClasses;
   private Boolean includeDeleted;
}
