package com.smarterfit.service;

import com.smarterfit.dto.request.AddressRequestDTO;
import com.smarterfit.dto.response.AddressResponseDTO;
import com.smarterfit.model.Address;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.AddressRepository;
import com.smarterfit.util.mapper.AddressMapper;
import com.smarterfit.util.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public AddressResponseDTO getAddressByUsername(String username) {
        User user = userValidation.validateUserByUsername(username);

        Profile profile = user.getProfile();
        Address address = profile.getAddress();

        return AddressMapper.toResponse(address);
    }

    @Transactional
    public AddressResponseDTO updateAddressByUsername(String username, AddressRequestDTO requestDTO) {
        User user = userValidation.validateUserByUsername(username);

        Profile profile = user.getProfile();
        Address address = AddressMapper.toEntity(requestDTO, profile.getAddress());
        address.setProfile(profile);
        addressRepository.save(address);

        return AddressMapper.toResponse(address);
    }
}
