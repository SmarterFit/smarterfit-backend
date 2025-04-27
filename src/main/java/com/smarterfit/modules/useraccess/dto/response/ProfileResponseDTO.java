package com.smarterfit.modules.useraccess.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.smarterfit.common.enums.Gender;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProfileResponseDTO(
        UUID id,
        String fullName,
        String cpf,
        String phone,
        LocalDate birthDate,
        Gender gender,
        AddressResponseDTO address) {
}