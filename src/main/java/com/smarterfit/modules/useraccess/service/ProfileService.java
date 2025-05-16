package com.smarterfit.modules.useraccess.service;

import com.smarterfit.common.util.CryptoUtil;
import com.smarterfit.modules.useraccess.dto.request.profile.SearchProfileRequestDTO;
import com.smarterfit.modules.useraccess.dto.request.profile.UpdateProfileRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.ProfileResponseDTO;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.mapper.ProfileMapper;
import com.smarterfit.modules.useraccess.repository.ProfileRepository;
import com.smarterfit.modules.useraccess.specification.ProfileSpecifications;
import com.smarterfit.modules.useraccess.validation.ProfileValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileValidation profileValidation;
    private final CryptoUtil cryptoUtil;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, ProfileValidation profileValidation,
            CryptoUtil cryptoUtil) {
        this.profileRepository = profileRepository;
        this.profileValidation = profileValidation;
        this.cryptoUtil = cryptoUtil;
    }

    @Transactional(readOnly = true)
    public ProfileResponseDTO getProfileById(UUID id) {
        Profile profile = profileValidation.validateProfileById(id);
        profile.setCpf(cryptoUtil.decrypt(profile.getCpf()));

        return ProfileMapper.toResponse(profile);
    }

    @Transactional(readOnly = true)
    public Page<ProfileResponseDTO> searchProfiles(SearchProfileRequestDTO requestDTO, Pageable pageable) {
        Specification<Profile> specification = ProfileSpecifications.searchByFilters(requestDTO);
        Page<Profile> profiles = profileRepository.findAll(
                specification, pageable);

        return profiles.map(profile -> {
            profile.setCpf(cryptoUtil.decrypt(profile.getCpf()));
            return ProfileMapper.toResponse(profile);
        });

    }

    @Transactional
    public ProfileResponseDTO updateProfile(UUID id, UpdateProfileRequestDTO requestDTO) {
        Profile profile = profileValidation.validateProfileById(id);

        String encryptedCpf = cryptoUtil.encrypt(requestDTO.getCpf());
        profileValidation.validateCpfAvailability(encryptedCpf, profile.getId());

        profile = ProfileMapper.toEntity(requestDTO, profile);
        profile.setCpf(encryptedCpf);
        profileRepository.save(profile);

        return ProfileMapper.toResponse(profile);
    }
}
