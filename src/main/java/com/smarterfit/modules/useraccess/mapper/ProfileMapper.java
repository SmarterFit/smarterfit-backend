package com.smarterfit.modules.useraccess.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.useraccess.dto.request.profile.UpdateProfileRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.AddressResponseDTO;
import com.smarterfit.modules.useraccess.dto.response.ProfileResponseDTO;
import com.smarterfit.modules.useraccess.entity.Profile;

public class ProfileMapper {

    public ProfileMapper() {
        // Private constructor to prevent instantiation
    }

    public static Profile toEntity(UpdateProfileRequestDTO dto, Profile profile) {
        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found.");
        }

        profile = GenericMapper.map(dto, profile);

        return profile;
    }

    public static ProfileResponseDTO toResponse(Profile profile) {
        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found.");
        }

        ProfileResponseDTO response = GenericMapper.map(profile, ProfileResponseDTO.class);

        if (profile.getAddress() != null) {
            AddressResponseDTO responseAddress = AddressMapper.toResponse(profile.getAddress());
            response = response.toBuilder().address(responseAddress).build();
        }

        return response;
    }
}
