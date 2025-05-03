package com.smarterfit.modules.classgroup.service;

import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupPlan;
import com.smarterfit.modules.classgroup.event.ClassGroupDeactivatedEvent;
import com.smarterfit.modules.classgroup.repository.ClassGroupPlanRepository;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClassGroupPlanService {
    private final ClassGroupPlanRepository classGroupPlanRepository;
    private final ValidationFaced validationFaced;
    private final ApplicationEventPublisher publisher;


    public ClassGroupPlanService(ClassGroupPlanRepository classGroupPlanRepository,
                             ValidationFaced validationFaced,
                             ApplicationEventPublisher publisher) {

        this.classGroupPlanRepository = classGroupPlanRepository;
        this.validationFaced = validationFaced;
        this.publisher = publisher;

    }

    @Transactional
    public void addPlanToClassGroup(UUID planId, UUID classGroupId) {
        validationFaced.classGroupPlanValidation.validateClassGroupPlanExists(planId, classGroupId);
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);

        Plan plan = validationFaced.planValidation.validatePlanById(planId);
        ClassGroupPlan classGroupPlan = new ClassGroupPlan(classGroup, plan);

        classGroupPlanRepository.save(classGroupPlan);
    }

    @Transactional
    public void removePlanToClassGroup(UUID planId, UUID classGroupId) {
        validationFaced.classGroupPlanValidation.validateClassGroupPlanNotExists(planId, classGroupId);

        ClassGroupPlan classGroupPlan = validationFaced.classGroupPlanValidation
                .validateClassGroupPlanById(planId, classGroupId);

        publisher.publishEvent(new ClassGroupDeactivatedEvent(classGroupPlan.getClassGroup()));

        classGroupPlanRepository.delete(classGroupPlan);
    }

}
