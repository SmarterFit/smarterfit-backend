package com.smarterfit.service;

import com.smarterfit.dto.request.ProfileRequestDTO;
import com.smarterfit.dto.response.ProfileResponseDTO;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.ProfileRepository;
import com.smarterfit.repository.UserRepository;
import com.smarterfit.util.mapper.ProfileMapper;
import com.smarterfit.util.validation.ProfileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileValidation profileValidation;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository,
                          ProfileValidation profileValidation) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.profileValidation = profileValidation;
    }


    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfileById(UUID id) {
        User user = findUserById(id);
        Profile profile = user.getProfile();

        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found for the user.");
        }

        return ProfileMapper.toResponse(profile);
    }

    @Transactional
    public ProfileResponseDTO updateProfile(UUID id, ProfileRequestDTO requestDTO) {
        User user = findUserById(id);

        Profile profile = user.getProfile();
        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found by username.");
        }

        // Validação extra opcional: evitar CPFs duplicados
        profileValidation.validateCpfAvailability(requestDTO.cpf(), profile.getId());

        profile = ProfileMapper.toEntity(requestDTO, profile);
        profile.setUser(user);

        profileRepository.save(profile);
        return ProfileMapper.toResponse(profile);
    }


    private User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }


}
