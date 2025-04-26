package com.smarterfit.specification;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.dto.request.subscription.SearchDTO;
import com.smarterfit.model.SubscriptionUser.Subscription;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class SubscriptionSpecifications {
   public static Specification<Subscription> searchByFilters(SearchDTO searchDTO) {
      return (root, query, criteriaBuilder) -> {
         List<Predicate> predicates = new ArrayList<>();

         // Filtro pelo id do dono
         if (searchDTO.ownerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("owner").get("id"), searchDTO.ownerId()));
         }

         // Filtro pelo id do participante
         if (searchDTO.participantId() != null) {
            predicates.add(
                  criteriaBuilder.equal(root.join("participants").get("user").get("id"), searchDTO.participantId()));
         }

         // Filtro pelo id do plano
         if (searchDTO.planId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("plan").get("id"), searchDTO.planId()));
         }

         // Filtro pelo status
         if (searchDTO.status() != null && !searchDTO.status().isEmpty()) {
            predicates.add(root.get("status").in(searchDTO.status()));
         }

         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }
}
