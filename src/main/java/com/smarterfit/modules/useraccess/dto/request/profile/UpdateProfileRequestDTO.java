package com.smarterfit.modules.useraccess.dto.request.profile;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.smarterfit.common.enums.Gender;

import jakarta.validation.constraints.NotBlank;
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
public class UpdateProfileRequestDTO {
   @NotBlank(message = "Full name must not be blank")
   @Size(max = 100, message = "Full name must be at most 100 characters long")
   private String fullName;

   @NotBlank(message = "CPF must not be blank")
   @CPF(message = "Invalid CPF")
   private String cpf;

   @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "Phone number must be in the format (99) 99999-9999 or (99) 9999-9999")
   private String phone;

   @Past(message = "Birth date must be in the past")
   private LocalDate birthDate;

   private Gender gender;
}
