package com.smarterfit.modules.useraccess.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record AddressResponseDTO(
                String street,
                String number,
                String neighborhood,
                String city,
                String cep,
                String state) {
}
