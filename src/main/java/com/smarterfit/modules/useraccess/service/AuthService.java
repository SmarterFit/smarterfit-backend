package com.smarterfit.modules.useraccess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.useraccess.dto.request.user.LoginRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.AuthResponseDTO;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.AuthMapper;
import com.smarterfit.modules.useraccess.validation.AuthValidation;

@Service
public class AuthService {
   private final AuthValidation authValidation;

   @Autowired
   public AuthService(AuthValidation authValidation) {
      this.authValidation = authValidation;
   }

   @Transactional(readOnly = true)
   public AuthResponseDTO login(LoginRequestDTO requestDTO) {
      User user = authValidation.validateUser(requestDTO.getEmail(), requestDTO.getPassword());

      return AuthMapper.toResponse("token", user);
   }
}
