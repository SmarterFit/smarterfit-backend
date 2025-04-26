package com.smarterfit.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.dto.request.plan.SearchDTO;
import com.smarterfit.model.Plan;

import jakarta.persistence.criteria.Predicate;

public class PlanSpecifications {
   public static Specification<Plan> searchByFilters(SearchDTO searchDTO) {
       return (root, query, criteriaBuilder) -> {
           List<Predicate> predicates = new ArrayList<>();

           // Filtro pelo nome (nameTerm)
           if (searchDTO.nameTerm() != null && !searchDTO.nameTerm().isEmpty()) {
               predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchDTO.nameTerm().toLowerCase() + "%"));
           }

           // Filtro pelo preço (minPrice, maxPrice)
           if (searchDTO.minPrice() != null) {
               predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchDTO.minPrice()));
           }
           if (searchDTO.maxPrice() != null) {
               predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchDTO.maxPrice()));
           }

           // Filtro pela duração (minDuration, maxDuration)
           if (searchDTO.minDuration() != null) {
               predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), searchDTO.minDuration()));
           }
           if (searchDTO.maxDuration() != null) {
               predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("duration"), searchDTO.maxDuration()));
           }

           // Filtro pelo número máximo de usuários (minMaxUsers, maxMaxUsers)
           if (searchDTO.minMaxUsers() != null) {
               predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxUsers"), searchDTO.minMaxUsers()));
           }
           if (searchDTO.maxMaxUsers() != null) {
               predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxUsers"), searchDTO.maxMaxUsers()));
           }

           // Filtro pelo número máximo de classes (minMaxClasses, maxMaxClasses)
           if (searchDTO.minMaxClasses() != null) {
               predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxClasses"), searchDTO.minMaxClasses()));
           }
           if (searchDTO.maxMaxClasses() != null) {
               predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxClasses"), searchDTO.maxMaxClasses()));
           }

           // Filtro para verificar se deve incluir ou não os planos deletados
           if (searchDTO.includeDeleted() == null || !searchDTO.includeDeleted()) {
               predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));
           }

           // Convertendo a lista de predicados em uma condição final
           return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
       };
   }
}