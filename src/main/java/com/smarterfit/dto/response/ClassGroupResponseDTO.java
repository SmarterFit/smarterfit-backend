package com.smarterfit.dto.response;

import java.time.LocalDate;
import java.util.UUID;

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
