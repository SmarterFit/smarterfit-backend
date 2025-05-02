package com.smarterfit.modules.useraccess.validation;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.repository.UserRepository;

@Component
public class AuthValidation {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   public AuthValidation(UserRepository userRepository,
         PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   public User validateUser(String email, String password) {
      String errorMessage = "E-mail or password is incorrect.";

      User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException(errorMessage));

      if (!passwordMatches(user, password)) {
         throw new ResourceNotFoundException(errorMessage);
      }

      return user;
   }

   private boolean passwordMatches(User user, String password) {
      return passwordEncoder.matches(password, user.getPassword());
   }
}
