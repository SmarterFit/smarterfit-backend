package com.smarterfit.modules.useraccess.mapper;

import com.smarterfit.common.dto.response.JwtToken;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.useraccess.dto.response.AuthResponseDTO;
import com.smarterfit.modules.useraccess.entity.Profile;

public class AuthMapper {
   private AuthMapper() {
      // Private constructor to prevent instantiation
   }

   public static AuthResponseDTO toResponse(JwtToken accessToken, Profile profile) {
      if (profile == null) {
         throw new ResourceNotFoundException("User not found.");
      }

      return new AuthResponseDTO(accessToken, ProfileMapper.toResponse(profile));
   }
}
