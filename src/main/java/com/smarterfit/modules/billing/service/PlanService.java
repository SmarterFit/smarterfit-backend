package com.smarterfit.modules.billing.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.billing.dto.request.plan.CreatePlanRequestDTO;
import com.smarterfit.modules.billing.dto.request.plan.SearchPlanRequestDTO;
import com.smarterfit.modules.billing.dto.request.plan.UpdatePlanRequestDTO;
import com.smarterfit.modules.billing.dto.response.plan.CreatedPlanResponseDTO;
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
   public CreatedPlanResponseDTO createPlan(CreatePlanRequestDTO requestDTO) {

      Plan plan = PlanMapper.toEntity(requestDTO);
      planRepository.save(plan);
      return PlanMapper.toResponse(plan);
   }

   @Transactional(readOnly = true)
   public CreatedPlanResponseDTO getPlanById(UUID id) {
      Plan plan = planValidation.validatePlanById(id);
      return PlanMapper.toResponse(plan);
   }

   @Transactional(readOnly = true)
   public List<CreatedPlanResponseDTO> getAllPlans() {
      return planRepository.findAll().stream()
            .map(PlanMapper::toResponse)
            .collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
   public Page<CreatedPlanResponseDTO> searchPlans(SearchPlanRequestDTO requestDTO, Pageable pageable) {
      Specification<Plan> specification = PlanSpecifications.searchByFilters(requestDTO);

      Page<Plan> plans = planRepository.findAll(specification, pageable);

      return plans.map(PlanMapper::toResponse);
   }

   @Transactional
   public CreatedPlanResponseDTO updatePlan(UUID id, UpdatePlanRequestDTO requestDTO) {
      Plan plan = planValidation.validatePlanById(id);
      plan = PlanMapper.toEntity(requestDTO, plan);

      planRepository.save(plan);
      return PlanMapper.toResponse(plan);
   }

   @Transactional
   public void deletePlan(UUID id) {
      Plan plan = planValidation.validatePlanById(id);

      planValidation.validatePlanNotDeleted(plan);
      ///planValidation.validateNoActiveSubscriptions(plan); Todas as assinaturas serão canceladas

      publisher.publishEvent(new PlanDeletedEvent(plan));

      plan.setDeletedAt(LocalDateTime.now());

      planRepository.save(plan);
   }
}
