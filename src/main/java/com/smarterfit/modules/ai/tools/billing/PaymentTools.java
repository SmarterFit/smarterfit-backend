package com.smarterfit.modules.ai.tools.billing;

import java.util.List;
import java.util.UUID;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.smarterfit.common.enums.PaymentMethod;
import com.smarterfit.common.enums.PaymentStatus;
import com.smarterfit.modules.billing.dto.request.payment.SearchPaymentRequestDTO;
import com.smarterfit.modules.billing.dto.response.payment.PaymentResponseDTO;
import com.smarterfit.modules.billing.service.PaymentService;

@Component
public class PaymentTools {
   private final PaymentService paymentService;

   public PaymentTools(PaymentService paymentService) {
      this.paymentService = paymentService;
   }

   @Tool(description = "Buscar pagamentos. Só preencha os parâmetros que forem explicitamente informados pelo usuário.")
   public List<PaymentResponseDTO> searchPayment(
         @ToolParam(required = true, description = "Id do proprietário") UUID ownerId,
         @ToolParam(required = false, description = "Métodos de pagamento") List<PaymentMethod> methods,
         @ToolParam(required = false, description = "Status de pagamento") List<PaymentStatus> status) {
      SearchPaymentRequestDTO request = new SearchPaymentRequestDTO();

      request.setSubscriptionOwnerId(ownerId);
      request.setMethods(methods);
      request.setStatus(status);

      return paymentService.searchPayments(request, Pageable.unpaged()).getContent();
   }
}
