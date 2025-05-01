package com.smarterfit.modules.classgroup.dto.request.modality;

import jakarta.validation.constraints.NotBlank;
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
public class CreateModalityRequestDTO {
        @NotBlank(message = "Modality cannot be blank")
        private String name;
}
