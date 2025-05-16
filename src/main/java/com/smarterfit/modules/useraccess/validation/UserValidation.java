package com.smarterfit.modules.useraccess.validation;

import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.repository.UserRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserValidation {
    private final UserRepository userRepository;

    public UserValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User validateUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    public User validateUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    public void validateEmailAvailability(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new ResourceAlreadyExistsException("E-mail is already in use.");
        });
    }

    public void validatePasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("The passwords don't match.");
        }
    }

    public void validateUserExists(UUID id) {
        if (userRepository.existsById(id)) {
            throw new BusinessException("User already exists");
        }
    }
}
