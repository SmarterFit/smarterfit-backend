package com.smarterfit.dto.request;

import java.util.List;

import com.smarterfit.enums.SubscriptionStatus;

import jakarta.validation.constraints.NotEmpty;

public record SubscriptionByStatusRequestDTO(
      @NotEmpty(message = "Status must not be empty") List<SubscriptionStatus> status) {
}
