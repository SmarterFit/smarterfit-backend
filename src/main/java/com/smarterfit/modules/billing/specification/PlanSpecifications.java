package com.smarterfit.modules.billing.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.modules.billing.dto.request.plan.SearchPlanRequestDTO;
import com.smarterfit.modules.billing.entity.Plan;

import jakarta.persistence.criteria.Predicate;

public class PlanSpecifications {
    public static Specification<Plan> searchByFilters(SearchPlanRequestDTO dto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro pelo nome (nameTerm)
            if (dto.nameTerm() != null && !dto.nameTerm().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + dto.nameTerm().toLowerCase() + "%"));
            }

            // Filtro pelo preço (minPrice, maxPrice)
            if (dto.minPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), dto.minPrice()));
            }
            if (dto.maxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), dto.maxPrice()));
            }

            // Filtro pela duração (minDuration, maxDuration)
            if (dto.minDuration() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), dto.minDuration()));
            }
            if (dto.maxDuration() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("duration"), dto.maxDuration()));
            }

            // Filtro pelo número máximo de usuários (minMaxUsers, maxMaxUsers)
            if (dto.minMaxUsers() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxUsers"), dto.minMaxUsers()));
            }
            if (dto.maxMaxUsers() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxUsers"), dto.maxMaxUsers()));
            }

            // Filtro pelo número máximo de classes (minMaxClasses, maxMaxClasses)
            if (dto.minMaxClasses() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxClasses"), dto.minMaxClasses()));
            }
            if (dto.maxMaxClasses() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxClasses"), dto.maxMaxClasses()));
            }

            // Filtro para verificar se deve incluir ou não os planos deletados
            if (dto.includeDeleted() == null || !dto.includeDeleted()) {
                predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
            }

            // Convertendo a lista de predicados em uma condição final
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}