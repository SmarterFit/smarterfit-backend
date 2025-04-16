package com.smarterfit.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ModalityRequestDTO(

        @NotBlank(message = "Modality ID cannot be blank")
        String name
) {
}
