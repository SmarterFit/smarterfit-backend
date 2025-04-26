package com.smarterfit.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.dto.request.payment.SearchDTO;
import com.smarterfit.model.SubscriptionUser.Payment;

import jakarta.persistence.criteria.Predicate;

public class PaymentSpecification {
   public static Specification<Payment> searchByFilters(SearchDTO searchDTO) {
      return (root, query, criteriaBuilder) -> {
         List<Predicate> predicates = new ArrayList<>();

         // Filtro pelo id da assinatura
         if (searchDTO.subscriptionId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subscription").get("id"), searchDTO.subscriptionId()));
         }

         // Filtro pelo id do dono da assinatura
         if (searchDTO.subscriptionOwnerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("subscription").get("owner").get("id"),
                  searchDTO.subscriptionOwnerId()));
         }

         // Filtro pelo método de pagamento
         if (searchDTO.paymentMethods() != null && !searchDTO.paymentMethods().isEmpty()) {
            predicates.add(root.get("paymentMethod").in(searchDTO.paymentMethods()));
         }

         // Filtro pelo status
         if (searchDTO.status() != null && !searchDTO.status().isEmpty()) {
            predicates.add(root.get("status").in(searchDTO.status()));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }

}
