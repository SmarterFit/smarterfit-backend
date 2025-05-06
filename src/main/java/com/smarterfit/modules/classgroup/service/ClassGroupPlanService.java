package com.smarterfit.modules.classgroup.service;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.classgroup.dto.request.classgroupplan.CreateClassGroupPlanDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupPlan;
import com.smarterfit.modules.classgroup.event.ClassGroupDeactivatedEvent;
import com.smarterfit.modules.classgroup.repository.ClassGroupPlanRepository;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;
import com.smarterfit.modules.useraccess.entity.UserRole;
import com.smarterfit.modules.useraccess.validation.RolesValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClassGroupPlanService {
    private final ClassGroupPlanRepository classGroupPlanRepository;
    private final ValidationFaced validationFaced;
    private final UserValidation userValidation;
    private final ApplicationEventPublisher publisher;


    public ClassGroupPlanService(ClassGroupPlanRepository classGroupPlanRepository,
                             ValidationFaced validationFaced,
                                UserValidation userValidation,
                             ApplicationEventPublisher publisher) {

        this.classGroupPlanRepository = classGroupPlanRepository;
        this.validationFaced = validationFaced;
        this.userValidation = userValidation;
        this.publisher = publisher;

    }

    @Transactional
    public void addPlanToClassGroup(CreateClassGroupPlanDTO requestDTO, UUID requesterId) {
        RolesValidation.validateUserRole(RoleType.ADMIN, userValidation.validateUserById(requesterId).getRoles());

        validationFaced.classGroupPlanValidation.validateClassGroupPlanExists(requestDTO.getPlanId(), requestDTO.getClassGroupId());
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());

        Plan plan = validationFaced.planValidation.validatePlanById(requestDTO.getPlanId());
        ClassGroupPlan classGroupPlan = new ClassGroupPlan(classGroup, plan);

        classGroupPlanRepository.save(classGroupPlan);
    }

    @Transactional
    public void removePlanToClassGroup(UUID planId, UUID classGroupId, UUID requesterId) {
        RolesValidation.validateUserRole(RoleType.ADMIN, userValidation.validateUserById(requesterId).getRoles());

        validationFaced.classGroupPlanValidation.validateClassGroupPlanNotExists(planId, classGroupId);

        ClassGroupPlan classGroupPlan = validationFaced.classGroupPlanValidation
                .validateClassGroupPlanById(planId, classGroupId);

        publisher.publishEvent(new ClassGroupDeactivatedEvent(classGroupPlan.getClassGroup()));

        classGroupPlanRepository.delete(classGroupPlan);
    }

}
