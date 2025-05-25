package com.smarterfit.modules.ai.tools.billing;

import java.util.List;
import java.util.UUID;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.billing.dto.response.subscription.SubscriptionResponseDTO;
import com.smarterfit.modules.billing.service.SubscriptionUserService;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

@Component
public class SubscriptionUserTools {
   private final SubscriptionUserService subscriptionUserService;

   public SubscriptionUserTools(SubscriptionUserService subscriptionUserService) {
      this.subscriptionUserService = subscriptionUserService;
   }

   @Tool(description = "Pegar todos os usuários de uma assinatura.")
   public List<UserResponseDTO> getAllUsersBySubscriptionId(
         @ToolParam(description = "ID da assinatura.") UUID subscriptionId) {
      return subscriptionUserService.getAllUsersBySubscriptionId(subscriptionId);
   }

   @Tool(description = "Pegar todas as assinaturas de um usuário.")
   public List<SubscriptionResponseDTO> getAllSubscriptionsByUserId(
         @ToolParam(description = "ID do usuário.") UUID userId) {
      return subscriptionUserService.getAllSubscriptionsByUserId(userId);
   }
}
