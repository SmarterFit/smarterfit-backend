/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.checkin.dto.request.GymCheckInAndCheckOutRequestDTO;
import com.smarterfit.modules.checkin.dto.response.GymCheckInResponseDTO;
import com.smarterfit.modules.checkin.service.GymCheckInService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/gym-check-in")
@CrossOrigin
public class GymCheckInController {
    private GymCheckInService gymCheckInService;

    @Autowired
    public GymCheckInController(GymCheckInService gymCheckInService) {
        this.gymCheckInService = gymCheckInService;
    }

    @PostMapping
    public ResponseEntity<GymCheckInResponseDTO> doCheckIn(
            @RequestBody @Valid GymCheckInAndCheckOutRequestDTO requestDTO) {
        GymCheckInResponseDTO responseDTO = gymCheckInService.doCheckIn(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PatchMapping("/check-out")
    public ResponseEntity<GymCheckInResponseDTO> doCheckOut(
            @RequestBody @Valid GymCheckInAndCheckOutRequestDTO requestDTO) {
        GymCheckInResponseDTO responseDTO = gymCheckInService.doCheckOut(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GymCheckInResponseDTO>> getAllByUserId(@RequestParam UUID userId) {
        List<GymCheckInResponseDTO> responseDTO = gymCheckInService.getAllByUserId(userId);
        return ResponseEntity.ok(responseDTO);
    }
}