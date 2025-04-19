package com.smarterfit.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record ClassGroupResponseDTO(

         UUID id,
         String name,
         Integer capacity,
         String groupType,
         String description,
         ModalityResponseDTO modalityDTO,
         Date startDate,
         Date endDate,
         String nameCreator


) {
}
