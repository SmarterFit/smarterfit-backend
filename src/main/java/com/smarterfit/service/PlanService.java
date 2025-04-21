package com.smarterfit.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.dto.request.PlanRequestDTO;
import com.smarterfit.dto.response.PlanResponseDTO;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Plan;
import com.smarterfit.repository.PlanRepository;
import com.smarterfit.util.mapper.PlanMapper;

@Service
public class PlanService {
   private final PlanRepository planRepository;

   @Autowired
   public PlanService(PlanRepository planRepository) {
      this.planRepository = planRepository;
   }

   @Transactional
   public PlanResponseDTO createPlan(PlanRequestDTO planRequestDTO) {
      Plan plan = PlanMapper.toEntity(planRequestDTO);
      planRepository.save(plan);
      return PlanMapper.toResponse(plan);
   }

   @Transactional(readOnly = true)
   public PlanResponseDTO getPlanById(UUID id) {
      Plan plan = findPlanById(id);
      return PlanMapper.toResponse(plan);
   }

   @Transactional(readOnly = true)
   public List<PlanResponseDTO> getAllPlans() {
      return planRepository.findAll().stream()
            .map(PlanMapper::toResponse)
            .collect(Collectors.toList());
   }

   @Transactional
   public PlanResponseDTO updatePlan(UUID id, PlanRequestDTO planRequestDTO) {
      Plan plan = findPlanById(id);
      plan = PlanMapper.toEntity(planRequestDTO, plan);

      planRepository.save(plan);
      return PlanMapper.toResponse(plan);
   }

   @Transactional
   public void deletePlan(UUID id) {
      Plan plan = findPlanById(id);
      planRepository.delete(plan);
   }

   private Plan findPlanById(UUID id) {
      return planRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
   }
}
