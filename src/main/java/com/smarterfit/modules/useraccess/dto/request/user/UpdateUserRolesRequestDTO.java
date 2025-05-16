package com.smarterfit.modules.useraccess.dto.request.user;

import java.util.Set;

import com.smarterfit.common.enums.RoleType;

import jakarta.validation.constraints.NotEmpty;
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
public class UpdateUserRolesRequestDTO {
   @NotEmpty(message = "At least one role must be provided")
   private Set<RoleType> roles;
}
