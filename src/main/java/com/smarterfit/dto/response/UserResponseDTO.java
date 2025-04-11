package com.smarterfit.dto.response;


import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
        String email,
        Set<String> roles,
        UUID ID
) {}