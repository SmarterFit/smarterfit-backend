package com.smarterfit.modules.ai.tools.billing;

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

   @Tool(description = "Buscar planos. Só preencha os parâmetros que forem explicitamente informados pelo usuário.")
   public List<CreatedPlanResponseDTO> searchPlans(
         @ToolParam(required = false, description = "Termo presente no nome (string)") String nameTerm,
         @ToolParam(required = false, description = "Preço mínimo (double)") Double minPrice,
         @ToolParam(required = false, description = "Preço máximo (double)") Double maxPrice,
         @ToolParam(required = false, description = "Duração mínima em dias (int)") Integer minDuration,
         @ToolParam(required = false, description = "Duração máxima em dias (int)") Integer maxDuration,
         @ToolParam(required = false, description = "Quantidade mínima de participantes em assinatura (int)") Integer minMaxUsers,
         @ToolParam(required = false, description = "Quantidade máxima de participantes em assinatura (int)") Integer maxMaxUsers,
         @ToolParam(required = false, description = "Quantidade mínima de turmas em assinatura (int)") Integer minMaxClasses,
         @ToolParam(required = false, description = "Quantidade máxima de turmas em assinatura (int)") Integer maxMaxClasses) {

      SearchPlanRequestDTO request = new SearchPlanRequestDTO();

      request.setNameTerm(nameTerm);
      request.setMinPrice(minPrice);
      request.setMaxPrice(maxPrice);
      request.setMinDuration(minDuration);
      request.setMaxDuration(maxDuration);
      request.setMinMaxUsers(minMaxUsers);
      request.setMaxMaxUsers(maxMaxUsers);
      request.setMinMaxClasses(minMaxClasses);
      request.setMaxMaxClasses(maxMaxClasses);

      return planService.searchPlans(request, Pageable.unpaged()).getContent();
   }
}
