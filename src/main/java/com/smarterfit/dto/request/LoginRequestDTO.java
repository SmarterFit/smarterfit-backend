package com.smarterfit.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @Email
        String email,

        @NotBlank(message = "The password must not be empty")
        String password
) {
}
