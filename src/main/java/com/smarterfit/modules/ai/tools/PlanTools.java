package com.smarterfit.modules.ai.tools;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.billing.dto.response.PlanResponseDTO;
import com.smarterfit.modules.billing.service.PlanService;

@Component
public class PlanTools {
   private PlanService planService;

   public PlanTools(PlanService planService) {
      this.planService = planService;
   }

   @Tool(description = "Pegar todos os planos disponíveis")
   public List<PlanResponseDTO> getAllPlans() {
      return planService.getAllPlans();
   }
}
