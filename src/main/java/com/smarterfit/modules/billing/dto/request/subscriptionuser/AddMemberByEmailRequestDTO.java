package com.smarterfit.modules.billing.dto.request.subscriptionuser;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
   @NotNull(message = "Subscription ID cannot be null")
   private UUID subscriptionId;

   @NotNull(message = "User e-mail cannot be null")
   @NotBlank(message = "User e-mail cannot be blank")
   @Email(message = "User e-mail must be a valid e-mail")
   private String userEmail;
}
