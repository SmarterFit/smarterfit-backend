package com.smarterfit.dto.response;


import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        Set<String> roles,
        Set<SubscriptionShortResponseDTO> ownedSubscriptions,
        Set<SubscriptionShortResponseDTO> participatingSubscriptions
) {}