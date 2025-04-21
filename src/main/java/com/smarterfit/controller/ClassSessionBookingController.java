package com.smarterfit.controller;

import com.smarterfit.dto.request.ClassSessionBookingRequestDTO;
import com.smarterfit.dto.request.ClassSessionBookingStatusDTO;
import com.smarterfit.dto.response.ClassSessionBookingResponseDTO;
import com.smarterfit.service.ClassSessionBookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/turma-aula/registro")
public class ClassSessionBookingController {

    private final ClassSessionBookingService classSessionBookingService;

    public ClassSessionBookingController(ClassSessionBookingService classSessionBookingService) {
        this.classSessionBookingService = classSessionBookingService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClassSessionBookingResponseDTO> createClassSessionBooking(@RequestBody @Valid ClassSessionBookingRequestDTO classSessionBookingRequest) {
        ClassSessionBookingResponseDTO responseDTO = classSessionBookingService.createClassSessionBooking(classSessionBookingRequest);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{userId}/{classSessionId}")
    public ResponseEntity<ClassSessionBookingResponseDTO> getClassSessionBookingById(@PathVariable UUID userId, @PathVariable UUID classSessionId) {
        return ResponseEntity.ok(classSessionBookingService.getClassSessionBookingById(userId, classSessionId));
    }

    @PutMapping
    public ResponseEntity<ClassSessionBookingResponseDTO> updateClassSessionBookingById(
            @RequestBody @Valid ClassSessionBookingStatusDTO dto) {
        return ResponseEntity.ok(classSessionBookingService.updateClassSessionBookingById(dto));
    }

    @GetMapping("/{classSessionId}/usuarios")
    public ResponseEntity<List<ClassSessionBookingResponseDTO>> getClassSessionBookingsByClassSessionId(@PathVariable UUID classSessionId) {
        return ResponseEntity.ok(classSessionBookingService.getAllBookingsToClassSession(classSessionId));
    }

}
