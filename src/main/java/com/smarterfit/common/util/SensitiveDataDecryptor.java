package com.smarterfit.common.util;

import org.springframework.stereotype.Component;

import com.smarterfit.modules.useraccess.dto.response.ProfileResponseDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

@Component
public class SensitiveDataDecryptor {

   private final CryptoUtil cryptoUtil;

   public SensitiveDataDecryptor(CryptoUtil cryptoUtil) {
      this.cryptoUtil = cryptoUtil;
   }

   /**
    * Descriptografa o CPF do Profile, se existir.
    */
   public ProfileResponseDTO decrypt(ProfileResponseDTO profile) {
      if (profile != null && profile.getCpf() != null) {
         profile.setCpf(cryptoUtil.decrypt(profile.getCpf()));
      }
      return profile;
   }

   /**
    * Descriptografa o Profile dentro do User.
    */
   public UserResponseDTO decrypt(UserResponseDTO user) {
      if (user != null && user.getProfile() != null) {
         decrypt(user.getProfile());
      }
      return user;
   }
}