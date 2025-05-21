package com.smarterfit.modules.ai.tools.billing;

import java.util.List;
import java.util.UUID;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.billing.dto.response.SubscriptionResponseDTO;
import com.smarterfit.modules.billing.service.SubscriptionService;

@Component
public class SubscriptionTools {
   private final SubscriptionService subscriptionService;

   public SubscriptionTools(SubscriptionService subscriptionService) {
      this.subscriptionService = subscriptionService;
   }

   @Tool(description = "Pegar todas as assinaturas que um usuário é dono.")
   public List<SubscriptionResponseDTO> getAllSubscriptionsByOwnerId(
         @ToolParam(description = "ID do dono.") UUID ownerId) {
      return subscriptionService.getAllSubscriptionsByOwnerId(ownerId);
   }
}
