package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.AddressRequestDTO;
import com.smarterfit.dto.response.AddressResponseDTO;
import com.smarterfit.model.Address;

public class AddressMapper {

    public static Address toEntity(AddressRequestDTO dto, Address address) {
        if (address == null) {
            address = new Address();
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
