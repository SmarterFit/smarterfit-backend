package com.smarterfit.modules.useraccess.dto.response;

import com.smarterfit.common.dto.response.JwtToken;

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
public class AuthResponseDTO {
      private JwtToken accessToken;
      private ProfileResponseDTO profile;
}
