package com.smarterfit.modules.training.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EquipmentResponseDTO {
    private UUID id;
    private String name;
}
