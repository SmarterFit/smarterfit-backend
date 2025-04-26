package com.smarterfit.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.dto.request.plan.PlanDTO;
import com.smarterfit.dto.request.plan.SearchDTO;
import com.smarterfit.dto.response.PlanResponseDTO;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.model.Plan;
import com.smarterfit.repository.PlanRepository;
import com.smarterfit.specification.PlanSpecifications;
import com.smarterfit.util.mapper.PlanMapper;
import com.smarterfit.util.validation.PlanValidation;

@Service
public class PlanService {
   private final PlanRepository planRepository;
   private final PlanValidation planValidation;

   @Autowired
   public PlanService(PlanRepository planRepository, PlanValidation planValidation) {
      this.planRepository = planRepository;
      this.planValidation = planValidation;
   }

   @Transactional
   public PlanResponseDTO createPlan(PlanDTO planRequestDTO) {
      Plan plan = PlanMapper.toEntity(planRequestDTO);
      planRepository.save(plan);
      return PlanMapper.toResponse(plan);
   }

   @Transactional(readOnly = true)
   public PlanResponseDTO getPlanById(UUID id) {
      Plan plan = planValidation.findPlanById(id);
      return PlanMapper.toResponse(plan);
   }

   @Transactional(readOnly = true)
   public List<PlanResponseDTO> getAllPlans() {
      return planRepository.findAll().stream()
            .map(PlanMapper::toResponse)
            .collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
   public Page<PlanResponseDTO> searchPlans(SearchDTO searchDTO, Pageable pageable) {
      Specification<Plan> specification = PlanSpecifications.searchByFilters(searchDTO);

      Page<Plan> plans = planRepository.findAll(specification, pageable);

      return plans.map(PlanMapper::toResponse);
   }

   @Transactional
   public PlanResponseDTO updatePlan(UUID id, PlanDTO planRequestDTO) {
      Plan plan = planValidation.findPlanById(id);
      plan = PlanMapper.toEntity(planRequestDTO, plan);

      planRepository.save(plan);
      return PlanMapper.toResponse(plan);
   }

   @Transactional
   public void deletePlan(UUID id) {
      Plan plan = planValidation.findPlanById(id);

      if (!plan.getSubscriptions().isEmpty()) {
         throw new BusinessException("Plan cannot be deleted because it has active subscriptions.");
      }

      if (plan.getDeletedAt() != null) {
         throw new BusinessException("Plan already deleted.");
      }

      plan.setDeletedAt(LocalDateTime.now());

      planRepository.save(plan);
   }
}
