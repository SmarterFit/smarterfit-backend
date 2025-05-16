package com.smarterfit.modules.useraccess.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.ProfileMetricType;

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
public class ProfileMetricResponseDTO {
   private UUID id;
   private ProfileMetricType type;
   private Double value;
   private LocalDateTime createdAt;
}
