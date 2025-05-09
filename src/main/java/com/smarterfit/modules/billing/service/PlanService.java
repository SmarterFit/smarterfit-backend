package com.smarterfit.modules.billing.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.RolesValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.billing.dto.request.plan.CreatePlanRequestDTO;
import com.smarterfit.modules.billing.dto.request.plan.SearchPlanRequestDTO;
import com.smarterfit.modules.billing.dto.response.PlanResponseDTO;
import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.billing.event.PlanDeletedEvent;
import com.smarterfit.modules.billing.mapper.PlanMapper;
import com.smarterfit.modules.billing.repository.PlanRepository;
import com.smarterfit.modules.billing.specification.PlanSpecifications;
import com.smarterfit.modules.billing.validation.PlanValidation;

@Service
public class PlanService {
   private final PlanRepository planRepository;
   private final PlanValidation planValidation;
   private final ApplicationEventPublisher publisher;

   @Autowired
   public PlanService(PlanRepository planRepository,
         PlanValidation planValidation,
         ApplicationEventPublisher publisher) {

      this.planRepository = planRepository;
      this.planValidation = planValidation;
      this.publisher = publisher;
   }

   @Transactional
   public PlanResponseDTO createPlan(CreatePlanRequestDTO requestDTO) {

      Plan plan = PlanMapper.toEntity(requestDTO);
      planRepository.save(plan);
      return PlanMapper.toResponse(plan);
   }

   @Transactional(readOnly = true)
   public PlanResponseDTO getPlanById(UUID id) {
      Plan plan = planValidation.validatePlanById(id);
      return PlanMapper.toResponse(plan);
   }

   @Transactional(readOnly = true)
   public List<PlanResponseDTO> getAllPlans() {
      return planRepository.findAll().stream()
            .map(PlanMapper::toResponse)
            .collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
   public Page<PlanResponseDTO> searchPlans(SearchPlanRequestDTO requestDTO, Pageable pageable) {
      Specification<Plan> specification = PlanSpecifications.searchByFilters(requestDTO);

      Page<Plan> plans = planRepository.findAll(specification, pageable);

      return plans.map(PlanMapper::toResponse);
   }

   @Transactional
   public PlanResponseDTO updatePlan(UUID id, CreatePlanRequestDTO requestDTO) {
      Plan plan = planValidation.validatePlanById(id);
      plan = PlanMapper.toEntity(requestDTO, plan);

      planRepository.save(plan);
      return PlanMapper.toResponse(plan);
   }

   @Transactional
   public void deletePlan(UUID id) {
      Plan plan = planValidation.validatePlanById(id);

      planValidation.validatePlanNotDeleted(plan);
      planValidation.validateNoActiveSubscriptions(plan);

      publisher.publishEvent(new PlanDeletedEvent(plan));

      plan.setDeletedAt(LocalDateTime.now());

      planRepository.save(plan);
   }
}
