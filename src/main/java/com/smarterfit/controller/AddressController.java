package com.smarterfit.controller;

import com.smarterfit.dto.request.AddressRequestDTO;
import com.smarterfit.dto.response.AddressResponseDTO;
import com.smarterfit.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<AddressResponseDTO> getAddress(@PathVariable String username) {
        return ResponseEntity.ok(addressService.getAddressByUsername(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable String username,
                                                            @RequestBody AddressRequestDTO dto) {
        return ResponseEntity.ok(addressService.updateAddressByUsername(username, dto));
    }
}
