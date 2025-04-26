package com.smarterfit.dto.response;

import java.util.Set;
import java.util.UUID;

import com.smarterfit.enums.RoleType;

public record UserResponseDTO(
      UUID id,
      String email,
      Set<RoleType> roles) {
}