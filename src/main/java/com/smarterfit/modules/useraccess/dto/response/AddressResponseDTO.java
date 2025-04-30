package com.smarterfit.modules.useraccess.dto.response;

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
public class AddressResponseDTO {
   private String street;
   private String number;
   private String neighborhood;
   private String city;
   private String cep;
   private String state;
}
