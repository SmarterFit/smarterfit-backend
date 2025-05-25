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

import com.smarterfit.modules.checkin.dto.request.ClassCheckInRequestDTO;
import com.smarterfit.modules.checkin.dto.response.ClassCheckInResponseDTO;
import com.smarterfit.modules.checkin.service.ClassCheckInService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/class-check-in")
@CrossOrigin
public class ClassCheckInController {
   ClassCheckInService classCheckInService;

   @Autowired
   public ClassCheckInController(ClassCheckInService classCheckInService) {
      this.classCheckInService = classCheckInService;
   }

   @PostMapping
   public ResponseEntity<ClassCheckInResponseDTO> createClassCheckIn(
         @RequestBody @Valid ClassCheckInRequestDTO requestDTO) {
      ClassCheckInResponseDTO responseDTO = classCheckInService.createClassCheckIn(requestDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
   }

   @PatchMapping
   public ResponseEntity<ClassCheckInResponseDTO> updateClassCheckIn(
         @RequestBody @Valid ClassCheckInRequestDTO requestDTO) {
      ClassCheckInResponseDTO responseDTO = classCheckInService.updateClassCheckIn(requestDTO);
      return ResponseEntity.ok(responseDTO);
   }

   @GetMapping("/user/{userId}")
   public ResponseEntity<List<ClassCheckInResponseDTO>> getAllByUserId(@RequestParam UUID userId) {
      List<ClassCheckInResponseDTO> responseDTO = classCheckInService.getAllByUserId(userId);
      return ResponseEntity.ok(responseDTO);
   }

   @GetMapping("/class-session/{classSessionId}")
   public ResponseEntity<List<ClassCheckInResponseDTO>> getAllByClassSessionId(@RequestParam UUID classSessionId) {
      List<ClassCheckInResponseDTO> responseDTO = classCheckInService.getAllByClassSessionId(classSessionId);
      return ResponseEntity.ok(responseDTO);
   }
}
