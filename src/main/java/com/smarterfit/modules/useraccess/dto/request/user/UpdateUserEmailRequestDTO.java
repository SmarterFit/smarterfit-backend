package com.smarterfit.modules.useraccess.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateUserEmailRequestDTO {
   @NotBlank(message = "Email must not be blank")
   @Email(message = "Email must be a valid format")
   private String email;
}
