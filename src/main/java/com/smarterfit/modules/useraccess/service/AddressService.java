package com.smarterfit.modules.useraccess.service;

import com.smarterfit.modules.useraccess.dto.request.address.CreateAddressRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.AddressResponseDTO;
import com.smarterfit.modules.useraccess.entity.Address;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.AddressMapper;
import com.smarterfit.modules.useraccess.repository.AddressRepository;
import com.smarterfit.modules.useraccess.validation.UserValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserValidation userValidation;

    @Autowired
    public AddressService(AddressRepository addressRepository, UserValidation userValidation) {
        this.addressRepository = addressRepository;
        this.userValidation = userValidation;
    }

    @Transactional(readOnly = true)
    public AddressResponseDTO getAddressByUserId(UUID id) {
        User user = userValidation.validateUserById(id);

        Profile profile = user.getProfile();
        Address address = profile.getAddress();

        return AddressMapper.toResponse(address);
    }

    @Transactional
    public AddressResponseDTO updateAddressByUserId(UUID id, CreateAddressRequestDTO requestDTO) {
        User user = userValidation.validateUserById(id);

        Profile profile = user.getProfile();
        Address address = AddressMapper.toEntity(requestDTO, profile.getAddress());
        address.setProfile(profile);
        addressRepository.save(address);

        return AddressMapper.toResponse(address);
    }
}
