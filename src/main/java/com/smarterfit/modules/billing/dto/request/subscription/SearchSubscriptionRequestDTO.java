package com.smarterfit.modules.billing.dto.request.subscription;

import java.util.List;
import java.util.UUID;

import com.smarterfit.common.enums.SubscriptionStatus;

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
public class SearchSubscriptionRequestDTO {
      private UUID ownerId;
      private UUID participantId;
      private UUID planId;
      private List<SubscriptionStatus> status;
}
