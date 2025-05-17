package com.smarterfit.modules.useraccess.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.modules.useraccess.dto.request.profile.SearchProfileRequestDTO;
import com.smarterfit.modules.useraccess.entity.Profile;

import jakarta.persistence.criteria.Predicate;

public class ProfileSpecifications {
   public static Specification<Profile> searchByFilters(SearchProfileRequestDTO dto) {
      return (root, query, criteriaBuilder) -> {
         List<Predicate> predicates = new ArrayList<>();

         // Filtro do nome do perfil
         if (dto.getFullNameTerm() != null && !dto.getFullNameTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")),
                  "%" + dto.getFullNameTerm().toLowerCase() + "%"));
         }

         // Filtro do CPF do perfil
         if (dto.getCpfTerm() != null && !dto.getCpfTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("cpf")),
                  "%" + dto.getCpfTerm().toLowerCase() + "%"));
         }

         // Filtro do telefone do perfil
         if (dto.getPhoneTerm() != null && !dto.getPhoneTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")),
                  "%" + dto.getPhoneTerm().toLowerCase() + "%"));
         }

         // Filtro do inicio data de nascimento do perfil
         if (dto.getBirthDateFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), dto.getBirthDateFrom()));
         }

         // Filtro do fim data de nascimento do perfil
         if (dto.getBirthDateTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), dto.getBirthDateTo()));
         }

         // Filtro do gênero do perfil
         if (dto.getGender() != null && !dto.getGender().isEmpty()) {
            predicates.add(root.get("gender").in(dto.getGender()));
         }

         // Convertendo a lista de predicados em uma condição final
         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }
}
