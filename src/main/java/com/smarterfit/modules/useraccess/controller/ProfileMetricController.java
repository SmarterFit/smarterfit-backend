package com.smarterfit.modules.useraccess.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.common.enums.ProfileMetricType;
import com.smarterfit.modules.useraccess.dto.request.profilemetric.CreateProfileMetricRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.ProfileMetricResponseDTO;
import com.smarterfit.modules.useraccess.service.ProfileMetricService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/perfis/metricas")
@CrossOrigin
public class ProfileMetricController {
   private final ProfileMetricService profileMetricService;

   @Autowired
   public ProfileMetricController(ProfileMetricService profileMetricService) {
      this.profileMetricService = profileMetricService;
   }

   @PostMapping("/{profileId}")
   public ResponseEntity<ProfileMetricResponseDTO> createProfileMetric(@PathVariable UUID profileId,
         @RequestBody @Valid CreateProfileMetricRequestDTO requestDTO) {
      return ResponseEntity.ok(profileMetricService.createProfileMetric(profileId, requestDTO));
   }

   @PostMapping("/{profileId}/ultimas")
   public ResponseEntity<List<ProfileMetricResponseDTO>> getLastsProfileMetricByProfileId(
         @PathVariable UUID profileId) {
      return ResponseEntity.ok(profileMetricService.getLastsProfileMetricByProfileId(profileId));
   }

   @PostMapping("/{profileId}/todas")
   public ResponseEntity<List<ProfileMetricResponseDTO>> getProfileMetricsByProfileId(@PathVariable UUID profileId) {
      return ResponseEntity.ok(profileMetricService.getProfileMetricsByProfileId(profileId));
   }

   @PostMapping("/{profileId}/tipo/{profileMetricType}")
   public ResponseEntity<List<ProfileMetricResponseDTO>> getProfileMetricsByProfileIdAndType(
         @PathVariable UUID profileId,
         @PathVariable ProfileMetricType type) {
      return ResponseEntity
            .ok(profileMetricService.getProfileMetricsByProfileIdAndType(profileId, type));
   }
}
