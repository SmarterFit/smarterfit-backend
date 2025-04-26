package com.smarterfit.util.validation.entity;

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

    public void validateClassGroupUserExists(UUID userId, UUID classGroupId) {
        if (classGroupUserRepository.existsByUserIdAndClassGroupId(userId, classGroupId)) {
            throw new ResourceAlreadyExistsException("User ID and Class Group ID already exist.");
        }

    }


    public ClassGroupUser validateClassGroupUserId(UUID userId, UUID classGroupId) {
        return classGroupUserRepository.findByUserIdAndClassGroupId(userId, classGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("This user is not a member of this class."));

    }
}
