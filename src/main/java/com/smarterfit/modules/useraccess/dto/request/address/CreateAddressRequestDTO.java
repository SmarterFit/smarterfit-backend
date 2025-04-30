package com.smarterfit.modules.useraccess.dto.request.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateAddressRequestDTO {
      @NotBlank(message = "Street must not be blank")
      @Size(max = 100, message = "Street must be at most 100 characters long")
      private String street;

      @NotBlank(message = "Number must not be blank")
      @Size(max = 10, message = "Number must be at most 10 characters long")
      private String number;

      @NotBlank(message = "Neighborhood must not be blank")
      @Size(max = 60, message = "Neighborhood must be at most 60 characters long")
      private String neighborhood;

      @NotBlank(message = "City must not be blank")
      @Size(max = 60, message = "City must be at most 60 characters long")
      private String city;

      @NotBlank(message = "Postal code must not be blank")
      private String cep;

      @NotBlank(message = "State must not be blank")
      @Size(min = 2, max = 2, message = "State must be a 2-letter abbreviation")
      private String state;
}
