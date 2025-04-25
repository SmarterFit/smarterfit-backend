package com.smarterfit.util.validation.entity;

import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Plan;
import com.smarterfit.repository.PlanRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
