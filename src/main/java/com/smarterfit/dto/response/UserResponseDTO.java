package com.smarterfit.dto.response;


import java.util.Set;

public record UserResponseDTO(
        String email,
        String username,
        Set<String> roles
) {}