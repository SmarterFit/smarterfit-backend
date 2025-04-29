package com.smarterfit.modules.classgroup.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;

/// TODO: Retornar horários agendados?

@Builder(toBuilder = true)
public record ClassGroupResponseDTO(
         UUID id,
         String title,
         Integer capacity,
         String description,
         ModalityResponseDTO modalityDTO,
         LocalDate startDate,
         LocalDate endDate,
         String nameCreator
) {
}
