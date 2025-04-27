package com.smarterfit.modules.billing.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.SubscriptionStatus;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

import lombok.Builder;

@Builder(toBuilder = true)
public record SubscriptionResponseDTO(
            UUID id,
            UserResponseDTO owner,
            LocalDateTime startedIn,
            LocalDateTime renewedIn,
            LocalDateTime endedIn,
            SubscriptionStatus status,
            Integer availableMembers,
            Integer availableClasses) {
}
