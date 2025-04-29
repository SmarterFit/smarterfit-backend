package com.smarterfit.dto.response;

import java.util.Set;
import java.util.UUID;

public record UserShortResponseDTO(
            UUID id,
            String email,
            Set<String> roles) {
}
