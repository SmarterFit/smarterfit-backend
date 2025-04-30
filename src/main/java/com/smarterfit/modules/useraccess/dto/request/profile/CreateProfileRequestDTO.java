package com.smarterfit.modules.useraccess.dto.request.profile;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.smarterfit.common.enums.Gender;
import com.smarterfit.modules.useraccess.dto.request.address.CreateAddressRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
public class CreateProfileRequestDTO {
   @NotBlank(message = "Full name must not be blank")
   @Size(max = 100, message = "Full name must be at most 100 characters long")
   private String fullName;

   @NotBlank(message = "CPF must not be blank")
   @CPF(message = "Invalid CPF")
   private String cpf;

   @NotBlank(message = "Phone number must not be blank")
   @Pattern(regexp = "\\d{10,11}", message = "Phone number must contain 10 or 11 digits")
   private String phone;

   @NotNull(message = "Birth date must not be null")
   @Past(message = "Birth date must be in the past")
   private LocalDate birthDate;

   @NotNull(message = "Gender must not be null")
   private Gender gender;

   @NotNull(message = "Address must not be null")
   @Valid
   private CreateAddressRequestDTO addresses;
}
