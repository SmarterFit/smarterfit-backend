package com.smarterfit.modules.useraccess.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.smarterfit.modules.useraccess.dto.request.profile.SearchProfileRequestDTO;
import com.smarterfit.modules.useraccess.entity.Profile;

import jakarta.persistence.criteria.Predicate;

public class ProfileSpecifications {
   public static Specification<Profile> searchByFilters(SearchProfileRequestDTO requestDTO) {
      return (root, query, criteriaBuilder) -> {
         List<Predicate> predicates = new ArrayList<>();

         // Filtro do nome do perfil
         if (requestDTO.getFullNameTerm() != null && !requestDTO.getFullNameTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")),
                  "%" + requestDTO.getFullNameTerm().toLowerCase() + "%"));
         }

         // Filtro do CPF do perfil
         if (requestDTO.getCpfTerm() != null && !requestDTO.getCpfTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("cpf")),
                  "%" + requestDTO.getCpfTerm().toLowerCase() + "%"));
         }

         // Filtro do telefone do perfil
         if (requestDTO.getPhoneTerm() != null && !requestDTO.getPhoneTerm().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")),
                  "%" + requestDTO.getPhoneTerm().toLowerCase() + "%"));
         }

         // Filtro do inicio data de nascimento do perfil
         if (requestDTO.getBirthDateFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), requestDTO.getBirthDateFrom()));
         }

         // Filtro do fim data de nascimento do perfil
         if (requestDTO.getBirthDateTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), requestDTO.getBirthDateTo()));
         }

         // Filtro do gênero do perfil
         if (requestDTO.getGender() != null && !requestDTO.getGender().isEmpty()) {
            predicates.add(root.get("gender").in(requestDTO.getGender()));
         }

         // Convertendo a lista de predicados em uma condição final
         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
   }
}
