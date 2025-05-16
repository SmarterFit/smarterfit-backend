package com.smarterfit.modules.useraccess.controller;

import com.smarterfit.modules.useraccess.dto.request.profile.SearchProfileRequestDTO;
import com.smarterfit.modules.useraccess.dto.request.profile.UpdateProfileRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.ProfileResponseDTO;
import com.smarterfit.modules.useraccess.service.ProfileService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/buscar")
    public ResponseEntity<Page<ProfileResponseDTO>> searchProfile(
            @RequestBody @Valid SearchProfileRequestDTO requestDTO,
            Pageable pageable) {
        return ResponseEntity.ok(profileService.searchProfiles(requestDTO, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable UUID id,
            @RequestBody @Valid UpdateProfileRequestDTO requestDTO) {
        return ResponseEntity.ok(profileService.updateProfile(id, requestDTO));
    }
}
