package com.smarterfit.modules.useraccess.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.smarterfit.common.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProfileResponseDTO {
    private UUID id;
    private String fullName;
    private String cpf;
    private String phone;
    private LocalDate birthDate;
    private Gender gender;
    private AddressResponseDTO address;
}
