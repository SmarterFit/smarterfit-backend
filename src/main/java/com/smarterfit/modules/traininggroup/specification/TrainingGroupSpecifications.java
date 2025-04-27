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
         if (requestDTO.nameTerm() != null && !requestDTO.nameTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                  "%" + requestDTO.nameTerm().toLowerCase() + "%"));
         }

         // Filtrar por membro do grupo
         if (requestDTO.userId() != null) {
            predicates.add(criteriaBuilder.equal(root.join("participants").get("user").get("id"), requestDTO.userId()));
         }

         // Filtro do tipo do grupo
         if (requestDTO.types() != null && !requestDTO.types().isEmpty()) {
            predicates.add(root.get("type").in(requestDTO.types()));
         }

         // Filtro para incluir grupos que já terminaram (padrão é não incluir)
         if (requestDTO.includeEnded() == null || !requestDTO.includeEnded()) {
            predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("endDate")),
                  criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now())));
         }

         // Filtro para incluir grupos que ainda não iniciaram (padrão é não incluir)
         if (requestDTO.includeNotStarted() == null || !requestDTO.includeNotStarted()) {
            predicates.add(criteriaBuilder.or(criteriaBuilder.isNull(root.get("startDate")),
                  criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), LocalDate.now())));
         }

         // Convertendo a lista de predicados em uma condição final
         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }
}
