package com.smarterfit.service;

import com.smarterfit.dto.request.ProfileRequestDTO;
import com.smarterfit.dto.response.ProfileResponseDTO;
import com.smarterfit.model.Profile;
import com.smarterfit.model.User;
import com.smarterfit.repository.ProfileRepository;
import com.smarterfit.util.mapper.ProfileMapper;
import com.smarterfit.util.validation.entity.ProfileValidation;
import com.smarterfit.util.validation.entity.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileValidation profileValidation;
    private final UserValidation userValidation;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, ProfileValidation profileValidation,
                          UserValidation userValidation) {
        this.profileRepository = profileRepository;
        this.profileValidation = profileValidation;
        this.userValidation = userValidation;
    }


    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfileById(UUID id) {
        User user = userValidation.validateUserById(id);
        Profile profile = user.getProfile();

        return ProfileMapper.toResponse(profile);
    }

    @Transactional
    public ProfileResponseDTO updateProfile(UUID id, ProfileRequestDTO requestDTO) {
        User user = userValidation.validateUserById(id);

        Profile profile = user.getProfile();

        // Validação extra opcional: evitar CPFs duplicados
        profileValidation.validateCpfAvailability(requestDTO.cpf(), profile.getId());

        profile = ProfileMapper.toEntity(requestDTO, profile);
        profile.setUser(user);

        profileRepository.save(profile);
        return ProfileMapper.toResponse(profile);
    }
}
