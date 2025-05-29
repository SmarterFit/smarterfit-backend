package com.smarterfit.modules.traininggroup.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
public class AddMemberByEmailRequestDTO {
   @NotNull(message = "Email cannot be null")
   @Email(message = "Invalid email format")
   private String email;

   @NotNull(message = "Training group ID cannot be null")
   private UUID trainingGroupId;
}
