package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.ProfileRequestDTO;
import com.smarterfit.dto.response.ProfileResponseDTO;
import com.smarterfit.model.Address;
import com.smarterfit.model.Profile;

public class ProfileMapper {
    public static Profile toEntity(ProfileRequestDTO dto, Profile profile) {
        if (profile == null) {
            profile = new Profile();
        }

        profile.setFullName(dto.fullName());
        profile.setCpf(dto.cpf());
        profile.setPhone(dto.phone());
        profile.setBirthDate(dto.birthDate());
        profile.setGender(dto.gender());

        if (dto.addresses() != null) {
            Address address = AddressMapper.toEntity(dto.addresses(), profile.getAddress());
            address.setProfile(profile);
            profile.setAddress(address);
        }

        return profile;
    }

    public static Profile toEntity(ProfileRequestDTO dto) {
        return toEntity(dto, null);
    }

    public static ProfileResponseDTO toResponse(Profile profile) {
        if (profile == null) {
            return null;
        }

        return new ProfileResponseDTO(
                profile.getId(),
                profile.getFullName(),
                profile.getCpf(),
                profile.getPhone(),
                profile.getBirthDate(),
                profile.getGender(),
                AddressMapper.toResponse(profile.getAddress()));
    }
}
