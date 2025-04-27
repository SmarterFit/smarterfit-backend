package com.smarterfit.modules.classgroup.dto.request.modality;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record CreateModalityRequestDTO(

        @NotBlank(message = "Modality cannot be blank")
        String name
) {
}
