package com.smarterfit.util.validation;

import com.smarterfit.exception.ResourceAlreadyExistsException;
import com.smarterfit.model.Plan;
import com.smarterfit.model.classGroupPlan.ClassGroupPlan;
import com.smarterfit.repository.ClassGroupPlanRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassGroupPlanValidation {

    public final ClassGroupPlanRepository  classGroupPlanRepository;

    public ClassGroupPlanValidation(ClassGroupPlanRepository classGroupPlanRepository) {
        this.classGroupPlanRepository = classGroupPlanRepository;
    }


    public void validateClassGroupPlanExists(UUID planId, UUID classGroupId) {
        if (classGroupPlanRepository.existsByPlanIdAndClassGroupId(planId, classGroupId)) {
            throw new ResourceAlreadyExistsException("Plan name already exists for this class group.");
        }
    }

    public ClassGroupPlan validateClassGroupPlanById(UUID planId, UUID classGroupId) {
        return classGroupPlanRepository.findByPlanIdAndClassGroupId(planId, classGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Class group plan not found."));

    }
}