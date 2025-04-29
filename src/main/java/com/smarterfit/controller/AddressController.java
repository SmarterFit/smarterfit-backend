package com.smarterfit.controller;

import com.smarterfit.dto.request.AddressRequestDTO;
import com.smarterfit.dto.response.AddressResponseDTO;
import com.smarterfit.service.AddressService;
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
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable UUID ID) {
        return ResponseEntity.ok(addressService.getAddressByUserId(ID));
    }

    @PutMapping("/{ID}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable UUID ID,
                                                            @RequestBody @Valid AddressRequestDTO dto) {
        return ResponseEntity.ok(addressService.updateAddressByUserId(ID, dto));
    }
}
