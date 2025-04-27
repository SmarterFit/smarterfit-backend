package com.smarterfit.modules.classgroup.dto.response;

import java.util.UUID;

import lombok.Builder;

@Builder(toBuilder = true)
public record ModalityResponseDTO(
        UUID id,
        String name
) {
}
