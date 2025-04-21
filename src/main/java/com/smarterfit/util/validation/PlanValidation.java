package com.smarterfit.util.validation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Plan;
import com.smarterfit.repository.PlanRepository;

@Component
public class PlanValidation {

   private final PlanRepository planRepository;

   public PlanValidation(PlanRepository planRepository) {
      this.planRepository = planRepository;
   }

   public Plan findPlanById(UUID id) {
      return planRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
   }
}
