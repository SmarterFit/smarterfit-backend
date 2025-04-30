package com.smarterfit.modules.useraccess.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.smarterfit.common.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
@Setter
    private LocalDate birthDate;
    private Gender gender;
    private AddressResponseDTO address;
}
