package com.smarterfit.modules.ai.tools;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.billing.dto.request.plan.SearchPlanRequestDTO;
import com.smarterfit.modules.billing.dto.response.plan.CreatedPlanResponseDTO;
import com.smarterfit.modules.billing.service.PlanService;

@Component
public class PlanTools {
   private final PlanService planService;

   public PlanTools(PlanService planService) {
      this.planService = planService;
   }

   @Tool(description = "Buscar planos disponíveis na academia com base em filtros.")
   public List<CreatedPlanResponseDTO> searchPlans(
         @ToolParam(description = "Duração mínima do plano em dias.") Integer minDuration,
         @ToolParam(description = "Duração máxima do plano em dias.") Integer maxDuration,
         @ToolParam(description = "Quantidade mínima de alunos.") Integer minUsers) {
      SearchPlanRequestDTO requestDTO = new SearchPlanRequestDTO();
      requestDTO.setMinDuration(minDuration);
      requestDTO.setMaxDuration(maxDuration);
      requestDTO.setMinMaxUsers(minUsers);

      return planService.searchPlans(requestDTO, Pageable.unpaged()).getContent();
   }
}
