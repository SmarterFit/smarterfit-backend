package com.smarterfit.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.dto.request.training_group.SearchDTO;
import com.smarterfit.model.TrainingGroup.TrainingGroup;

import jakarta.persistence.criteria.Predicate;

public class TrainingGroupSpecifications {
   public static Specification<TrainingGroup> searchByFilters(SearchDTO searchDTO) {
      return (root, query, criteriaBuilder) -> {
         List<Predicate> predicates = new ArrayList<>();

         // Filtro do nome do grupo
         if (searchDTO.nameTerm() != null && !searchDTO.nameTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                  "%" + searchDTO.nameTerm().toLowerCase() + "%"));
         }

         // Filtrar por membro do grupo
         if (searchDTO.userId() != null) {
            predicates.add(criteriaBuilder.equal(root.join("participants").get("user").get("id"), searchDTO.userId()));
         }

         // Filtro do tipo do grupo
         if (searchDTO.groupTypes() != null && !searchDTO.groupTypes().isEmpty()) {
            predicates.add(root.get("groupType").in(searchDTO.groupTypes()));
         }

         // Filtro para incluir grupos que já terminaram (padrão é não incluir)
         if (searchDTO.includeEnded() == null || !searchDTO.includeEnded()) {
            predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("endDate")),
                  criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now())));
         }

         // Filtro para incluir grupos que ainda não iniciaram (padrão é não incluir)
         if (searchDTO.includeNotStarted() == null || !searchDTO.includeNotStarted()) {
            predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("startDate")),
                  criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), LocalDate.now())));
         }

         // Convertendo a lista de predicados em uma condição final
         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }
}
