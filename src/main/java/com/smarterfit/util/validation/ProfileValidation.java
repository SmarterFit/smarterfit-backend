package com.smarterfit.util.validation;

import com.smarterfit.exception.BusinessException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Profile;
import com.smarterfit.model.User;
import com.smarterfit.repository.ProfileRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProfileValidation {

    private final ProfileRepository profileRepository;

    public ProfileValidation(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void validateCpfAvailability(String cpf) {
        profileRepository.findByCpf(cpf).ifPresent(p -> {
            throw new BusinessException("CPF is already in use.");
        });
    }
    public void validateCpfAvailability(String cpf, UUID currentProfileId) {
        profileRepository.findByCpf(cpf).ifPresent(existing -> {
            if (!existing.getId().equals(currentProfileId)) {
                throw new BusinessException("The CPF is already in use.");
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
