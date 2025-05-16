package com.smarterfit.modules.useraccess.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UpdateUserPasswordRequestDTO {
   @NotBlank(message = "Password must not be blank")
   @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
   private String password;

   @NotBlank(message = "Password must not be blank")
   @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
   private String confirmPassword;
}
