package com.smarterfit.modules.billing.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.modules.billing.dto.request.payment.SearchPaymentRequestDTO;
import com.smarterfit.modules.billing.entity.Payment;

import jakarta.persistence.criteria.Predicate;

public class PaymentSpecifications {
   public static Specification<Payment> searchByFilters(SearchPaymentRequestDTO dto) {
      return (root, query, criteriaBuilder) -> {
         List<Predicate> predicates = new ArrayList<>();

         // Filtro pelo id da assinatura
         if (dto.getSubscriptionId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subscription").get("id"), dto.getSubscriptionId()));
         }

         // Filtro pelo id do dono da assinatura
         if (dto.getSubscriptionOwnerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subscription").get("owner").get("id"),
                  dto.getSubscriptionOwnerId()));
         }

         // Filtro pelo método de pagamento
         if (dto.getMethods() != null && !dto.getMethods().isEmpty()) {
            predicates.add(root.get("method").in(dto.getMethods()));
         }

         // Filtro pelo status
         if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            predicates.add(root.get("status").in(dto.getStatus()));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }

}
