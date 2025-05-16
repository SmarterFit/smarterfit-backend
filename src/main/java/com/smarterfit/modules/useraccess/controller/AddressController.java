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

    @PostMapping("/{profileId}")
    public ResponseEntity<AddressResponseDTO> createAddress(@PathVariable UUID profileId,
            @RequestBody @Valid CreateAddressRequestDTO requestDTO) {
        return ResponseEntity.ok(addressService.createAddressByProfileId(profileId, requestDTO));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable UUID profileId) {
        return ResponseEntity.ok(addressService.getAddressByProfileId(profileId));
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable UUID profileId,
            @RequestBody @Valid CreateAddressRequestDTO requestDTO) {
        return ResponseEntity.ok(addressService.updateAddressByProfileId(profileId, requestDTO));
    }
}
