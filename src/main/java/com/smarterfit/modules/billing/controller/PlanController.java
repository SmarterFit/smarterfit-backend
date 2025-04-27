package com.smarterfit.modules.billing.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.billing.dto.request.plan.CreatePlanRequestDTO;
import com.smarterfit.modules.billing.dto.request.plan.SearchPlanRequestDTO;
import com.smarterfit.modules.billing.dto.response.PlanResponseDTO;
import com.smarterfit.modules.billing.service.PlanService;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/planos")
@CrossOrigin
public class PlanController {
   private final PlanService planService;

   @Autowired
   public PlanController(PlanService planService) {
      this.planService = planService;
   }

   @PostMapping
   public ResponseEntity<PlanResponseDTO> createPlan(@RequestBody @Valid CreatePlanRequestDTO requestDTO) {
      PlanResponseDTO responseDTO = planService.createPlan(requestDTO);
      return ResponseEntity.status(201).body(responseDTO);
   }

   @GetMapping("/{id}")
   public ResponseEntity<PlanResponseDTO> getPlanById(@PathVariable UUID id) {
      return ResponseEntity.ok(planService.getPlanById(id));
   }

   @GetMapping
   public ResponseEntity<List<PlanResponseDTO>> getAllPlans() {
      return ResponseEntity.ok(planService.getAllPlans());
   }

   @GetMapping("/buscar")
   public ResponseEntity<Page<PlanResponseDTO>> searchPlans(@ModelAttribute SearchPlanRequestDTO requestDTO,
         Pageable pageable) {
      return ResponseEntity.ok(planService.searchPlans(requestDTO, pageable));
   }

   @PutMapping("/{id}")
   public ResponseEntity<PlanResponseDTO> updatePlan(@PathVariable UUID id,
         @RequestBody @Valid CreatePlanRequestDTO requestDTO) {
      return ResponseEntity.ok(planService.updatePlan(id, requestDTO));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletePlan(@PathVariable UUID id) {
      planService.deletePlan(id);
      return ResponseEntity.noContent().build();
   }
}
