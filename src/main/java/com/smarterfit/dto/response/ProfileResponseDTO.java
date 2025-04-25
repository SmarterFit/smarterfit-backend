package com.smarterfit.dto.response;

import com.smarterfit.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record ProfileResponseDTO(
        String fullName,
        String cpf,
        String phone,
        LocalDate birthDate,
        Gender gender,
        AddressResponseDTO address,
        UUID id
) {}
