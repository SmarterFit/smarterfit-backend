package com.smarterfit.controller;

import com.smarterfit.dto.request.ProfileRequestDTO;
import com.smarterfit.dto.response.ProfileResponseDTO;
import com.smarterfit.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/perfis")
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable UUID id,
                                                            @RequestBody @Valid ProfileRequestDTO requestDTO) {
        return ResponseEntity.ok(profileService.updateProfile(id, requestDTO));
    }
}
