package com.smarterfit.modules.useraccess.dto.request.profilemetric;

import com.smarterfit.common.enums.ProfileMetricType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateProfileMetricRequestDTO {
   @NotNull(message = "Type must not be null")
   private ProfileMetricType type;

   @NotNull(message = "Value must not be null")
   private Double value;
}
