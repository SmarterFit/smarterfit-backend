package com.smarterfit.util.validation;

import com.smarterfit.exception.BusinessException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserValidation {

    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User validateUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    public User validateUserByById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    public void validateEmailAvailability(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new BusinessException("E-mail is already in use.");
        });
    }

    public void validateUsernameAvailability(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new BusinessException("Username is already in use.");
        });
    }

    public void validatePasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new BusinessException("The passwords don't match.");
        }
    }

}

