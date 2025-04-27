package com.smarterfit.modules.useraccess.service;

import com.smarterfit.modules.useraccess.dto.request.profile.CreateProfileRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.ProfileResponseDTO;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.ProfileMapper;
import com.smarterfit.modules.useraccess.repository.ProfileRepository;
import com.smarterfit.modules.useraccess.validation.ProfileValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;

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
    public ProfileResponseDTO updateProfile(UUID id, CreateProfileRequestDTO requestDTO) {
        User user = userValidation.validateUserById(id);

        Profile profile = user.getProfile();

        // Validação extra opcional: evitar CPFs duplicados
        // TODO: Validar apenas se for diferente if (!profile.getCpf().equals(requestDTO.cpf()))
        profileValidation.validateCpfAvailability(requestDTO.cpf(), profile.getId());

        profile = ProfileMapper.toEntity(requestDTO, profile);
        profile.setUser(user);

        profileRepository.save(profile);
        return ProfileMapper.toResponse(profile);
    }
}
