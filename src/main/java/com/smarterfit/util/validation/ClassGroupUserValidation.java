package com.smarterfit.util.validation;

import com.smarterfit.exception.ResourceAlreadyExistsException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.classGroupUser.ClassGroupUser;
import com.smarterfit.repository.ClassGroupUserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClassGroupUserValidation {

    public final ClassGroupUserRepository classGroupUserRepository;

    public ClassGroupUserValidation(ClassGroupUserRepository classGroupUserRepository) {
        this.classGroupUserRepository = classGroupUserRepository;
    }

    public void validateClassGroupUser(UUID userId, UUID classGroupId) {
        if (classGroupUserRepository.existsByUserIdAndClassGroupId(userId, classGroupId)) {
            throw new ResourceAlreadyExistsException("User ID and Class Group ID already exist.");
        }

    }

    public ClassGroupUser validateClassGroupUserId(UUID userId, UUID classGroupId) {
        return classGroupUserRepository.findByUserIdAndClassGroupId(userId, classGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("Class group user not found."));

    }
}
