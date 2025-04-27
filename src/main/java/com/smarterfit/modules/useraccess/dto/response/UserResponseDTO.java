package com.smarterfit.modules.useraccess.dto.response;

import java.util.Set;
import java.util.UUID;

import com.smarterfit.common.enums.RoleType;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserResponseDTO(
      UUID id,
      String email,
      Set<RoleType> roles) {
}
