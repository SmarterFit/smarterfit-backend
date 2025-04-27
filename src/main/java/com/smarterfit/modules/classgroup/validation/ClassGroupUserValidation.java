package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.classgroup.entity.ClassGroupUser;
import com.smarterfit.modules.classgroup.repository.ClassGroupUserRepository;

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
