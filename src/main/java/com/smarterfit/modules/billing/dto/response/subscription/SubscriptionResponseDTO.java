package com.smarterfit.modules.billing.dto.response.subscription;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.SubscriptionStatus;
import com.smarterfit.modules.billing.dto.response.plan.CreatedPlanResponseDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

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
public class SubscriptionResponseDTO {
   private UUID id;
   private UserResponseDTO owner;
   private CreatedPlanResponseDTO plan;
   private LocalDateTime startedIn;
   private LocalDateTime renewedIn;
   private LocalDateTime endedIn;
   private SubscriptionStatus status;
   private Integer availableMembers;
   private Integer availableClasses;
}
