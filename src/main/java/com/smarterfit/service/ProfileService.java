package com.smarterfit.service;

import com.smarterfit.dto.request.ProfileRequestDTO;
import com.smarterfit.dto.response.ProfileResponseDTO;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.ProfileRepository;
import com.smarterfit.repository.UserRepository;
import com.smarterfit.util.mapper.AddressMapper;
import com.smarterfit.util.mapper.ProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ProfileResponseDTO getProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = user.getProfile();
        return ProfileMapper.toResponse(profile);
    }

    public ProfileResponseDTO updateProfile(String username, ProfileRequestDTO requestDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = user.getProfile();
        profile = ProfileMapper.toEntity(requestDTO, profile);

        profile.setUser(user);
        profileRepository.save(profile);

        return ProfileMapper.toResponse(profile);
    }
}
