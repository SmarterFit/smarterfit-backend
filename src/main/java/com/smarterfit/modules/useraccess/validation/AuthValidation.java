package com.smarterfit.modules.useraccess.validation;

import org.springframework.stereotype.Component;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.repository.UserRepository;

@Component
public class AuthValidation {
   private final UserRepository userRepository;

   public AuthValidation(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   public User validateUser(String email, String password) {
      String errorMessage = "E-mail or password is incorrect.";

      User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException(errorMessage));

      if (!user.getPassword().equals(password)) {
         throw new ResourceNotFoundException(errorMessage);
      }

      return user;
   }
}
