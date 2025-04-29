package com.smarterfit.modules.billing.mapper;

import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.billing.dto.request.plan.CreatePlanRequestDTO;
import com.smarterfit.modules.billing.dto.response.PlanResponseDTO;
import com.smarterfit.modules.billing.entity.Plan;

public class PlanMapper {
   private PlanMapper() {
      // Private constructor to prevent instantiation
   }

   public static Plan toEntity(CreatePlanRequestDTO dto) {
      return toEntity(dto, new Plan());
   }

   public static Plan toEntity(CreatePlanRequestDTO dto, Plan plan) {
      if (plan == null) {
         throw new ResourceNotFoundException("Plan not found.");
      }

      plan = GenericMapper.map(dto, plan);

      return plan;
   }

   public static PlanResponseDTO toResponse(Plan plan) {
      try {
         if (plan == null) {
            throw new ResourceNotFoundException("Plan not found.");
         }
   
         return GenericMapper.map(plan, PlanResponseDTO.class);
      } catch (Exception e) {
         throw new BusinessException("Erro no mapping do response");
      }
   }
}
