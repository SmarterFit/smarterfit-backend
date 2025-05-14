package com.smarterfit.modules.classgroup.service;

import com.smarterfit.modules.billing.dto.response.plan.CreatedPlanResponseDTO;
import com.smarterfit.modules.billing.dto.response.plan.PlanResponseDTO;
import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.billing.repository.PlanRepository;
import com.smarterfit.modules.classgroup.dto.request.classgroupplan.CreateClassGroupPlanDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupPlan;
import com.smarterfit.modules.classgroup.event.ClassGroupDeactivatedEvent;
import com.smarterfit.modules.classgroup.mapper.ClassGroupPlanMapper;
import com.smarterfit.modules.classgroup.repository.ClassGroupPlanRepository;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Service
public class ClassGroupPlanService {
    private final ClassGroupPlanRepository classGroupPlanRepository;
    public final PlanRepository planRepository;
    private final ValidationFaced validationFaced;
    private final ApplicationEventPublisher publisher;


    public ClassGroupPlanService(ClassGroupPlanRepository classGroupPlanRepository,
                             ValidationFaced validationFaced,
                             ApplicationEventPublisher publisher,
                             PlanRepository planRepository) {

        this.classGroupPlanRepository = classGroupPlanRepository;
        this.validationFaced = validationFaced;
        this.planRepository = planRepository;
        this.publisher = publisher;

    }

    @Transactional
    public void addPlanToClassGroup(CreateClassGroupPlanDTO requestDTO) {

        validationFaced.classGroupPlanValidation.validateClassGroupPlanExists(requestDTO.getPlanId(), requestDTO.getClassGroupId());
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(requestDTO.getClassGroupId());

        Plan plan = validationFaced.planValidation.validatePlanById(requestDTO.getPlanId());
        ClassGroupPlan classGroupPlan = new ClassGroupPlan(classGroup, plan);

        classGroupPlanRepository.save(classGroupPlan);
    }

    @Transactional(readOnly = true)
    public List<PlanResponseDTO> getPlansToClassGroup(UUID classGroupId) {
        validationFaced.classGroupValidation.existsClassGroupById(classGroupId);

        List<ClassGroupPlan> classGroupPlans = classGroupPlanRepository.findAllByClassGroupId(classGroupId);

        List<UUID> planIds = classGroupPlans.stream()
                .map(cgp -> cgp.getPlan().getId())
                .collect(Collectors.toList());

        List<Plan> plans = planRepository.findAllById(planIds);

        // Mapear os planos para DTOs
        return plans.stream()
                .map(ClassGroupPlanMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Transactional
    public void removePlanToClassGroup(UUID planId, UUID classGroupId) {
        validationFaced.classGroupPlanValidation.validateClassGroupPlanNotExists(planId, classGroupId);

        ClassGroupPlan classGroupPlan = validationFaced.classGroupPlanValidation
                .validateClassGroupPlanById(planId, classGroupId);

        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);

//        TODO: FIX BUG
//        publisher.publishEvent(new ClassGroupDeactivatedEvent(classGroup));

        classGroupPlanRepository.delete(classGroupPlan);
    }

}
