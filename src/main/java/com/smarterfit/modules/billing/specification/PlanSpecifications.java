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
            if (dto.getNameTerm() != null && !dto.getNameTerm().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + dto.getNameTerm().toLowerCase() + "%"));
            }

            // Filtro pelo preço (minPrice, maxPrice)
            if (dto.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), dto.getMinPrice()));
            }
            if (dto.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), dto.getMaxPrice()));
            }

            // Filtro pela duração (minDuration, maxDuration)
            if (dto.getMinDuration() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), dto.getMinDuration()));
            }
            if (dto.getMaxDuration() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("duration"), dto.getMaxDuration()));
            }

            // Filtro pelo número máximo de usuários (minMaxUsers, maxMaxUsers)
            if (dto.getMinMaxClasses() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxUsers"), dto.getMinMaxClasses()));
            }
            if (dto.getMaxMaxUsers() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxUsers"), dto.getMaxMaxUsers()));
            }

            // Filtro pelo número máximo de classes (minMaxClasses, maxMaxClasses)
            if (dto.getMinMaxClasses() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxClasses"), dto.getMinMaxClasses()));
            }
            if (dto.getMaxMaxClasses() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxClasses"), dto.getMaxMaxClasses()));
            }

            // Filtro para verificar se deve incluir ou não os planos deletados
            if (dto.getIncludeDeleted() == null || !dto.getIncludeDeleted()) {
                predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
            }

            // Convertendo a lista de predicados em uma condição final
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}