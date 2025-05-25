package com.smarterfit.modules.useraccess.dto.response;

import java.util.Set;
import java.util.UUID;

import com.smarterfit.common.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserResponseDTO {
      private UUID id;
      private String email;
      private Set<RoleType> roles;
      private ProfileResponseDTO profile;
}
