package com.smarterfit.modules.useraccess.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.repository.ProfileRepository;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProfileValidation {

    private final ProfileRepository profileRepository;

    public ProfileValidation(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile validateProfileById(UUID id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException("Profile not found.");
        });
    }

    public boolean existsById (UUID id) {
        return profileRepository.existsById(id);
    }

    public void validateCpfAvailability(String cpf) {
        profileRepository.findByCpf(cpf).ifPresent(p -> {
            throw new ResourceAlreadyExistsException("CPF is already in use.");
        });
    }

    public void validateCpfAvailability(String cpf, UUID currentProfileId) {
        profileRepository.findByCpf(cpf).ifPresent(existing -> {
            if (!existing.getId().equals(currentProfileId)) {
                throw new ResourceAlreadyExistsException("The CPF is already in use.");
            }
        });
    }

    public Profile validateProfile(User user) {
        Profile profile = user.getProfile();

        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found for user.");
        }

        return profile;
    }
}
