package com.smarterfit.modules.training.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EquipmentRequestDTO {

    @NotBlank(message = "Equipment name cannot be blank")
    private String name;

}
