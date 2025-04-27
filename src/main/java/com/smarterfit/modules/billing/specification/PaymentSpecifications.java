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
         if (dto.subscriptionId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subscription").get("id"), dto.subscriptionId()));
         }

         // Filtro pelo id do dono da assinatura
         if (dto.subscriptionOwnerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subscription").get("owner").get("id"),
                  dto.subscriptionOwnerId()));
         }

         // Filtro pelo método de pagamento
         if (dto.methods() != null && !dto.methods().isEmpty()) {
            predicates.add(root.get("method").in(dto.methods()));
         }

         // Filtro pelo status
         if (dto.status() != null && !dto.status().isEmpty()) {
            predicates.add(root.get("status").in(dto.status()));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }

}
