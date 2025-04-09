package com.smarterfit.controller;

import com.smarterfit.dto.request.ProfileRequestDTO;
import com.smarterfit.dto.response.ProfileResponseDTO;
import com.smarterfit.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfis")
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable String username) {
        return ResponseEntity.ok(profileService.getProfileByUsername(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable String username,
                                                            @RequestBody ProfileRequestDTO requestDTO) {
        return ResponseEntity.ok(profileService.updateProfile(username, requestDTO));
    }
}
