package com.smarterfit.modules.useraccess.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoginRequestDTO(
                @Email String email,

                @NotBlank(message = "The password must not be empty") String password) {
}