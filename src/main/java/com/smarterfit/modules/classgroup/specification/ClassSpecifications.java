package com.smarterfit.modules.classgroup.specification;


import com.smarterfit.modules.classgroup.dto.request.classgroup.SearchClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClassSpecifications {

    public static Specification<ClassGroup> searchByFilters(SearchClassGroupRequestDTO dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por título
            if (dto.getTitleTerm() != null && !dto.getTitleTerm().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + dto.getTitleTerm().toLowerCase() + "%"));
            }

            // Filtro por capacidade mínima/máxima
            if (dto.getMinCapacity() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("capacity"), dto.getMinCapacity()));
            }
            if (dto.getMaxCapacity() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("capacity"), dto.getMaxCapacity()));
            }

            // Filtro por modalidade (nome)
            if (dto.getModality() != null && !dto.getModality().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("modality").get("name")), "%" + dto.getModality().toLowerCase() + "%"));
            }

            // Filtro por número mínimo/máximo de membros (precisa de join ou campo calculado)
            if (dto.getMinTotalMembers() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("totalMembers"), dto.getMinTotalMembers()));
            }
            if (dto.getMaxTotalMembers() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("totalMembers"), dto.getMaxTotalMembers()));
            }

            // Filtro por dias da semana (existe em outra tabela: join com schedules)
            if (dto.getDaysOfWeek() != null && !dto.getDaysOfWeek().isEmpty()) {
                root.join("schedules").get("dayOfWeek"); // apenas para garantir o join
                predicates.add(root.join("schedules").get("dayOfWeek").in(dto.getDaysOfWeek()));
                query.distinct(true); // evitar duplicatas
            }

            // Filtro por data de início
            if (dto.getStartDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), dto.getStartDateFrom()));
            }
            if (dto.getStartDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), dto.getStartDateTo()));
            }

            // Filtro por data de fim
            if (dto.getEndDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("endDate"), dto.getEndDateFrom()));
            }
            if (dto.getEndDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), dto.getEndDateTo()));
            }

            // Filtro por evento
            if (dto.getIsEvent() != null) {
                predicates.add(cb.equal(root.get("isEvent"), !dto.getIsEvent()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
