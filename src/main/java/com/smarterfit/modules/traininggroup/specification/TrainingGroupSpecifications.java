package com.smarterfit.modules.traininggroup.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.modules.traininggroup.dto.request.SearchTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.entity.TrainingGroup;

import jakarta.persistence.criteria.Predicate;

public class TrainingGroupSpecifications {
   public static Specification<TrainingGroup> searchByFilters(SearchTrainingGroupRequestDTO requestDTO) {
      return (root, query, criteriaBuilder) -> {
         List<Predicate> predicates = new ArrayList<>();

         // Filtro do nome do grupo
         if (requestDTO.getNameTerm() != null && !requestDTO.getNameTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                  "%" + requestDTO.getNameTerm().toLowerCase() + "%"));
         }

         // Filtrar por membro do grupo
         if (requestDTO.getUserId() != null) {
            predicates
                  .add(criteriaBuilder.equal(root.join("participants").get("user").get("id"), requestDTO.getUserId()));
         }

         // Filtro do tipo do grupo
         if (requestDTO.getTypes() != null && !requestDTO.getTypes().isEmpty()) {
            predicates.add(root.get("type").in(requestDTO.getTypes()));
         }

         // Filtro para incluir grupos que já terminaram (padrão é não incluir)
         if (requestDTO.getIncludeEnded() == null || !requestDTO.getIncludeEnded()) {
            predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("endDate")),
                  criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now())));
         }

         // Filtro para incluir grupos que ainda não iniciaram (padrão é não incluir)
         if (requestDTO.getIncludeNotStarted() == null || !requestDTO.getIncludeNotStarted()) {
            predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("startDate")),
                  criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), LocalDate.now())));
         }

         // Convertendo a lista de predicados em uma condição final
         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }
}
