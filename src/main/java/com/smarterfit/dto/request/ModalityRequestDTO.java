package com.smarterfit.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ModalityRequestDTO(

        @NotBlank(message = "Modality cannot be blank")
        String name
) {
}
