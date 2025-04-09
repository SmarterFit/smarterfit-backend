package com.smarterfit.service;

import com.smarterfit.dto.request.AddressRequestDTO;
import com.smarterfit.dto.response.AddressResponseDTO;
import com.smarterfit.model.Address;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.AddressRepository;
import com.smarterfit.repository.UserRepository;
import com.smarterfit.util.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public AddressResponseDTO getAddressByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = user.getProfile();
        Address address = profile.getAddress();

        return AddressMapper.toResponse(address);
    }

    public AddressResponseDTO updateAddressByUsername(String username, AddressRequestDTO requestDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = user.getProfile();

        Address address = profile.getAddress();
        address = AddressMapper.toEntity(requestDTO, address);
        address.setProfile(profile); // garantir vínculo

        addressRepository.save(address);

        return AddressMapper.toResponse(address);
    }
}
