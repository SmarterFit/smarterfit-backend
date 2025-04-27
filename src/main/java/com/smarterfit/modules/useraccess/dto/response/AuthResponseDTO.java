package com.smarterfit.modules.useraccess.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record AuthResponseDTO(
      String token,
      UserResponseDTO user) {
}
