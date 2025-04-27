package com.smarterfit.modules.billing.dto.response.subscriptionuser;

import com.smarterfit.modules.billing.dto.response.SubscriptionResponseDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

import lombok.Builder;

@Builder(toBuilder = true)
public record SubscriptionUserResponseDTO(
      UserResponseDTO user,
      SubscriptionResponseDTO subscription) {
}
