package com.smarterfit.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

public record UserRequestDTO(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be a valid format")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
        String password,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
        String confirmPassword,

        @NotBlank(message = "Password must not be blank")
        @CPF(message = "Invalid CPF")
        String cpf,

        @NotBlank(message = "user must not be blank")
        @Size(min = 8, max = 50, message = "username must have at least 8 characters")
        String username,

        @NotEmpty(message = "At least one role must be provided")
        Set<@NotBlank(message = "Role cannot be blank") String> roles
) {
}
