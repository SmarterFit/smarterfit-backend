package com.smarterfit.modules.billing.specification;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.modules.billing.dto.request.subscription.SearchSubscriptionRequestDTO;
import com.smarterfit.modules.billing.entity.Subscription;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class SubscriptionSpecifications {
   public static Specification<Subscription> searchByFilters(SearchSubscriptionRequestDTO dto) {
      return (root, query, criteriaBuilder) -> {
         List<Predicate> predicates = new ArrayList<>();

         // Filtro pelo id do dono
         if (dto.ownerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("owner").get("id"), dto.ownerId()));
         }

         // Filtro pelo id do participante
         if (dto.participantId() != null) {
            predicates.add(
                  criteriaBuilder.equal(root.join("participants").get("user").get("id"), dto.participantId()));
         }

         // Filtro pelo id do plano
         if (dto.planId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("plan").get("id"), dto.planId()));
         }

         // Filtro pelo status
         if (dto.status() != null && !dto.status().isEmpty()) {
            predicates.add(root.get("status").in(dto.status()));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }
}
