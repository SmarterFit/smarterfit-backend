package com.smarterfit.modules.billing.dto.request.plan;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class UpdatePlanRequestDTO {
   @NotNull(message = "The name must not be null")
   @Length(min = 3, max = 50, message = "The name must be between 3 and 50 characters long")
   private String name;

   @Length(max = 255, message = "The description must be at most 255 characters long")
   private String description;

   @NotNull(message = "The price must not be null")
   @DecimalMin(value = "0.0", message = "The price must be a positive number")
   private Double price;
}
