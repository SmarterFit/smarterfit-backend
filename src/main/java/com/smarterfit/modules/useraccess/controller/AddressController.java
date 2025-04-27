package com.smarterfit.modules.useraccess.controller;

import com.smarterfit.modules.useraccess.dto.request.address.CreateAddressRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.AddressResponseDTO;
import com.smarterfit.modules.useraccess.service.AddressService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.getAddressByUserId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable UUID id,
            @RequestBody @Valid CreateAddressRequestDTO requestDTO) {
        return ResponseEntity.ok(addressService.updateAddressByUserId(id, requestDTO));
    }
}
