package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.PlanRequestDTO;
import com.smarterfit.dto.response.PlanResponseDTO;
import com.smarterfit.model.Plan;

public class PlanMapper {
   public static Plan toEntity(PlanRequestDTO planRequestDTO) {
      return toEntity(planRequestDTO, null);
   }

   public static Plan toEntity(PlanRequestDTO planRequestDTO, Plan plan) {
      if (planRequestDTO == null) {
         return null;
      }

      if (plan == null) {
         plan = new Plan();
      }

      plan.setName(planRequestDTO.name());
      plan.setDescription(planRequestDTO.description());
      plan.setPrice(planRequestDTO.price());
      plan.setDuration(planRequestDTO.duration());
      plan.setMaxUsers(planRequestDTO.maxUsers());
      
      return plan;
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
            plan.getMaxUsers()
      );
      
      return planResponseDTO;
   }
}
