package com.smarterfit.modules.training.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EquipmentResponseDTO {
    private String id;
    private String name;
}
