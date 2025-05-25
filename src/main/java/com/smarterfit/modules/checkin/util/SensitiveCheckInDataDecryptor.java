package com.smarterfit.modules.checkin.util;

import org.springframework.stereotype.Component;

import com.smarterfit.common.util.CryptoUtil;
import com.smarterfit.common.util.SensitiveDataDecryptor;
import com.smarterfit.modules.checkin.dto.response.ClassCheckInResponseDTO;
import com.smarterfit.modules.checkin.dto.response.GymCheckInResponseDTO;

@Component
public class SensitiveCheckInDataDecryptor extends SensitiveDataDecryptor {
   public SensitiveCheckInDataDecryptor(CryptoUtil cryptoUtil) {
      super(cryptoUtil);
   }

   public GymCheckInResponseDTO decrypt(GymCheckInResponseDTO gymCheckIn) {
      if (gymCheckIn != null && gymCheckIn.getUser() != null) {
         decrypt(gymCheckIn.getUser());
      }
      return gymCheckIn;
   }

   public ClassCheckInResponseDTO decrypt(ClassCheckInResponseDTO classCheckIn) {
      if (classCheckIn != null && classCheckIn.getUser() != null) {
         decrypt(classCheckIn.getUser());
      }
      return classCheckIn;
   }
}
