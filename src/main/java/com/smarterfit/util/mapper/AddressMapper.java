package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.AddressRequestDTO;
import com.smarterfit.dto.response.AddressResponseDTO;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Address;

public class AddressMapper {

    private AddressMapper() {
        // Private constructor to prevent instantiation
    }

    public static Address toEntity(AddressRequestDTO dto, Address address) {
        if (address == null) {
            throw new ResourceNotFoundException("Address not found.");
        }

        address.setStreet(dto.street());
        address.setNumber(dto.number());
        address.setNeighborhood(dto.neighborhood());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setCep(dto.cep());

        return address;
    }

    public static AddressResponseDTO toResponse(Address address) {
        return new AddressResponseDTO(
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCep()
        );
    }
}
