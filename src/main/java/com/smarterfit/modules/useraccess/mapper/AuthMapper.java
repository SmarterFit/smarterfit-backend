package com.smarterfit.modules.useraccess.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.useraccess.dto.response.AuthResponseDTO;
import com.smarterfit.modules.useraccess.entity.User;

public class AuthMapper {
   private AuthMapper() {
      // Private constructor to prevent instantiation
   }

   public static AuthResponseDTO toResponse(String token, User user) {
      if (user == null) {
         throw new ResourceNotFoundException("User not found.");
      }

      return new AuthResponseDTO(token, UserMapper.toResponse(user));
   }
}
