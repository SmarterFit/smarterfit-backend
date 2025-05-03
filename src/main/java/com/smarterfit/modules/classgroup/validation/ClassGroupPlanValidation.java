package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.modules.classgroup.entity.ClassGroupPlan;
import com.smarterfit.modules.classgroup.repository.ClassGroupPlanRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassGroupPlanValidation {

    public final ClassGroupPlanRepository classGroupPlanRepository;

    public ClassGroupPlanValidation(ClassGroupPlanRepository classGroupPlanRepository) {
        this.classGroupPlanRepository = classGroupPlanRepository;
    }

    public void validateClassGroupPlanExists(UUID planId, UUID classGroupId) {
        if (classGroupPlanRepository.existsByPlanIdAndClassGroupId(planId, classGroupId)) {
            throw new ResourceAlreadyExistsException("Plan name already exists for this class group.");
        }
    }

    public void validateClassGroupPlanNotExists(UUID planId, UUID classGroupId) {
        if (!classGroupPlanRepository.existsByPlanIdAndClassGroupId(planId, classGroupId)) {
            throw new ResourceAlreadyExistsException("Plan name not exists for this class group.");
        }
    }

    public ClassGroupPlan validateClassGroupPlanById(UUID planId, UUID classGroupId) {
        return classGroupPlanRepository.findByPlanIdAndClassGroupId(planId, classGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Class group plan not found."));

    }
}