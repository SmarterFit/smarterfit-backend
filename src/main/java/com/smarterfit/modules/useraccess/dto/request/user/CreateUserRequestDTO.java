package com.smarterfit.modules.useraccess.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.br.CPF;

import com.smarterfit.common.enums.RoleType;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateUserRequestDTO {
   @NotBlank(message = "Name must not be blank")
   @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
   private String name;

   @NotBlank(message = "Email must not be blank")
   @Email(message = "Email must be a valid format")
   private String email;

   @NotBlank(message = "Password must not be blank")
   @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
   private String password;

   @NotBlank(message = "Password must not be blank")
   @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
   private String confirmPassword;

   @NotBlank(message = "CPF must not be blank")
   @CPF(message = "Invalid CPF")
   private String cpf;

   @NotEmpty(message = "At least one role must be provided")
   private Set<RoleType> roles;
}
