package com.smarterfit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record AddressRequestDTO(
        @NotBlank(message = "Street must not be blank")
        @Size(max = 100, message = "Street must be at most 100 characters long")
        String street,

        @NotBlank(message = "Number must not be blank")
        @Size(max = 10, message = "Number must be at most 10 characters long")
        String number,

        @NotBlank(message = "Neighborhood must not be blank")
        @Size(max = 60, message = "Neighborhood must be at most 60 characters long")
        String neighborhood,

        @NotBlank(message = "City must not be blank")
        @Size(max = 60, message = "City must be at most 60 characters long")
        String city,

        @NotBlank(message = "Postal code must not be blank")
        String cep,

        @NotBlank(message = "State must not be blank")
        @Size(min = 2, max = 2, message = "State must be a 2-letter abbreviation")
        String state
) {}
