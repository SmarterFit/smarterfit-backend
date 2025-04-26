package com.smarterfit.controller;

import com.smarterfit.dto.request.ClassEventBookingRequestDTO;
import com.smarterfit.dto.request.ClassEventBookingStatusDTO;
import com.smarterfit.dto.response.ClassEventBookingResponseDTO;
import com.smarterfit.service.ClassEventBookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/aula-evento/registro")
public class ClassEventBookingController {

    private final ClassEventBookingService classEventBookingService;

    public ClassEventBookingController(ClassEventBookingService classEventBookingService) {
        this.classEventBookingService = classEventBookingService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClassEventBookingResponseDTO> createClassEventBooking(@RequestBody @Valid ClassEventBookingRequestDTO classEventBookingRequest) {
        ClassEventBookingResponseDTO responseDTO = classEventBookingService.createClassEventBooking(classEventBookingRequest);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{userId}/{classEventId}")
    public ResponseEntity<ClassEventBookingResponseDTO> getClassEventBookingById(@PathVariable UUID userId, @PathVariable UUID classEventId) {
        return ResponseEntity.ok(classEventBookingService.getClassEventBookingById(userId, classEventId));
    }

    @PutMapping
    public ResponseEntity<ClassEventBookingResponseDTO> updateClassEventBookingById(
            @RequestBody @Valid ClassEventBookingStatusDTO dto) {
        return ResponseEntity.ok(classEventBookingService.updateClassEventBookingById(dto));
    }

    @GetMapping("/{classEventId}/usuarios")
    public ResponseEntity<List<ClassEventBookingResponseDTO>> getClassEventBookingsByClassEventId(@PathVariable UUID classEventId) {
        return ResponseEntity.ok(classEventBookingService.getAllBookingsToClassEvent(classEventId));
    }

}
