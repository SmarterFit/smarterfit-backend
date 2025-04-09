package com.smarterfit.dto.request;

import com.smarterfit.enums.Gender;

import java.time.LocalDate;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public record ProfileRequestDTO(
        @NotBlank(message = "Full name must not be blank")
        @Size(max = 100, message = "Full name must be at most 100 characters long")
        String fullName,

        @NotBlank(message = "CPF must not be blank")
        @Pattern(regexp = "\\d{11}", message = "CPF must contain 11 digits only")
        String cpf,

        @NotBlank(message = "Phone number must not be blank")
        @Pattern(regexp = "\\d{10,11}", message = "Phone number must contain 10 or 11 digits")
        String phone,

        @NotNull(message = "Birth date must not be null")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotNull(message = "Gender must not be null")
        Gender gender,

        @NotNull(message = "Address must not be null")
        @Valid
        AddressRequestDTO addresses
) {}
