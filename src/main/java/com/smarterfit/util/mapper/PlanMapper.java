package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.plan.PlanDTO;
import com.smarterfit.dto.response.PlanResponseDTO;
import com.smarterfit.model.Plan;

public class PlanMapper {
   public static Plan toEntity(PlanDTO dto, Plan plan) {
      if (plan == null) {
         plan = new Plan();
      }

      plan.setName(dto.name());
      plan.setDescription(dto.description());
      plan.setPrice(dto.price());
      plan.setDuration(dto.duration());
      plan.setMaxUsers(dto.maxUsers());
      plan.setMaxClasses(dto.maxClasses());

      return plan;
   }

   public static Plan toEntity(PlanDTO dto) {
      return toEntity(dto, null);
   }

   public static PlanResponseDTO toResponse(Plan plan) {
      if (plan == null) {
         return null;
      }

      PlanResponseDTO planResponseDTO = new PlanResponseDTO(
            plan.getId(),
            plan.getName(),
            plan.getDescription(),
            plan.getPrice(),
            plan.getDuration(),
            plan.getMaxUsers(),
            plan.getMaxClasses(),
            plan.getDeletedAt());

      return planResponseDTO;
   }
}
