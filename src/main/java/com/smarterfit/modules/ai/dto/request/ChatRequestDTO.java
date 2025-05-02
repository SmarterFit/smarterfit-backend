package com.smarterfit.modules.ai.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record ChatRequestDTO(
   @NotBlank
   String prompt
) {
}