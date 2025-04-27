package com.smarterfit.modules.useraccess.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.useraccess.dto.request.address.CreateAddressRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.AddressResponseDTO;
import com.smarterfit.modules.useraccess.entity.Address;

public class AddressMapper {

    private AddressMapper() {
        // Private constructor to prevent instantiation
    }

    public static Address toEntity(CreateAddressRequestDTO dto) {
        return toEntity(dto, new Address());
    }

    public static Address toEntity(CreateAddressRequestDTO dto, Address address) {
        if (address == null) {
            throw new ResourceNotFoundException("Address not found.");
        }

        address = GenericMapper.map(dto, address);

        return address;
    }

    public static AddressResponseDTO toResponse(Address address) {
        if (address == null) {
            throw new ResourceNotFoundException("Address not found.");
        }

        return GenericMapper.map(address, AddressResponseDTO.class);
    }
}
