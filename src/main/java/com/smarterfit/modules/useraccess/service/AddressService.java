package com.smarterfit.modules.useraccess.service;

import com.smarterfit.modules.useraccess.dto.request.address.CreateAddressRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.AddressResponseDTO;
import com.smarterfit.modules.useraccess.entity.Address;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.mapper.AddressMapper;
import com.smarterfit.modules.useraccess.repository.AddressRepository;
import com.smarterfit.modules.useraccess.validation.ProfileValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ProfileValidation profileValidation;

    @Autowired
    public AddressService(AddressRepository addressRepository, ProfileValidation profileValidation) {
        this.addressRepository = addressRepository;
        this.profileValidation = profileValidation;
    }

    @Transactional
    public AddressResponseDTO createAddressByProfileId(UUID profileId, CreateAddressRequestDTO requestDTO) {
        Profile profile = profileValidation.validateProfileById(profileId);

        Address address = AddressMapper.toEntity(requestDTO, profile);
        addressRepository.save(address);

        return AddressMapper.toResponse(address);
    }

    @Transactional(readOnly = true)
    public AddressResponseDTO getAddressByProfileId(UUID profileId) {
        Profile profile = profileValidation.validateProfileById(profileId);

        Address address = profile.getAddress();

        return AddressMapper.toResponse(address);
    }

    @Transactional
    public AddressResponseDTO updateAddressByProfileId(UUID profileId, CreateAddressRequestDTO requestDTO) {
        Profile profile = profileValidation.validateProfileById(profileId);

        Address address = AddressMapper.toEntity(requestDTO, profile, profile.getAddress());
        addressRepository.save(address);

        return AddressMapper.toResponse(address);
    }
}
