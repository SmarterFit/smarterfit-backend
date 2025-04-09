package com.smarterfit.dto.response;

public record AddressResponseDTO(
        String street,
        String number,
        String neighborhood,
        String city,
        String cep,
        String state
) {}
