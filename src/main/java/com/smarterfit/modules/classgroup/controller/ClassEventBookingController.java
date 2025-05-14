package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.modules.classgroup.dto.request.classevent.booking.CreateClassEventBookingRequestDTO;
import com.smarterfit.modules.classgroup.dto.request.classevent.booking.UpdateClassEventBookingRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassEventBookingResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassEventBookingService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/eventos")
public class ClassEventBookingController {

    private final ClassEventBookingService classEventBookingService;

    public ClassEventBookingController(ClassEventBookingService classEventBookingService) {
        this.classEventBookingService = classEventBookingService;
    }

    @PostMapping("/reservas/realizar")
    public ResponseEntity<ClassEventBookingResponseDTO> createClassEventBooking(
            @RequestBody @Valid CreateClassEventBookingRequestDTO requestDTO) {
        ClassEventBookingResponseDTO responseDTO = classEventBookingService.createClassEventBooking(requestDTO);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{userId}/reservas/{classEventId}")
    public ResponseEntity<ClassEventBookingResponseDTO> getClassEventBookingById(@PathVariable UUID userId,
            @PathVariable UUID classEventId) {
        return ResponseEntity.ok(classEventBookingService.getClassEventBookingById(userId, classEventId));
    }

    @PutMapping
    public ResponseEntity<ClassEventBookingResponseDTO> updateClassEventBookingById(
            @RequestBody @Valid UpdateClassEventBookingRequestDTO requestDTO) {
        return ResponseEntity.ok(classEventBookingService.updateClassEventBookingById(requestDTO));
    }

    @GetMapping("/reservas/{classEventId}/usuarios")
    public ResponseEntity<List<ClassEventBookingResponseDTO>> getClassEventBookingsByClassEventId(
            @PathVariable UUID classEventId) {
        return ResponseEntity.ok(classEventBookingService.getAllBookingsToClassEvent(classEventId));
    }

}
