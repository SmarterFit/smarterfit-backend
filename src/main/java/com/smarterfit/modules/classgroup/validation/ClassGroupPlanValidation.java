package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.classgroup.entity.ClassGroupPlan;
import com.smarterfit.modules.classgroup.repository.ClassGroupPlanRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
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

    public void validateClassGroupPlanAndSubscription(UUID classGroupId, UUID subscriptionPlanId) {
        List<ClassGroupPlan> classGroupPlans = classGroupPlanRepository.findAllByClassGroupId(classGroupId);
        boolean planExistsInGroup = classGroupPlans.stream()
                .anyMatch(cgp -> cgp.getPlan().getId().equals(subscriptionPlanId));

        if (!planExistsInGroup) {
            throw new BusinessException("Subscription plan is not associated with this class group.");
        }
    }


}