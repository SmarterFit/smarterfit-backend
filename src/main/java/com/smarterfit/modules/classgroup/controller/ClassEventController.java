package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
import com.smarterfit.modules.classgroup.dto.request.classevent.CreateClassEventRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassEventResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassEventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("turma/eventos")
public class ClassEventController {

    private final ClassEventService classEventService;

    public ClassEventController(ClassEventService classEventService) {
        this.classEventService = classEventService;
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PostMapping("/cadastrar")
    public ResponseEntity<ClassEventResponseDTO> createClassEvent(
            @RequestBody @Valid CreateClassEventRequestDTO requestDTO) {
        ClassEventResponseDTO response = classEventService.createClassEvent(requestDTO);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassEventResponseDTO> getClassEventById(@PathVariable UUID id) {
        ClassEventResponseDTO response = classEventService.getClassEventById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClassEventResponseDTO>> getAllClassEvents() {
        List<ClassEventResponseDTO> events = classEventService.getAllClassEvents();
        return ResponseEntity.ok(events);
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClassEventById(
            @PathVariable UUID id,
            @RequestBody @Valid CreateClassEventRequestDTO requestDTO) {
        classEventService.updateClassEventById(id, requestDTO);
        return ResponseEntity.ok().build();
    }

    @RequireRole(RoleType.EMPLOYEE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassEventById(@PathVariable UUID id) {
        classEventService.deleteClassEventById(id);
        return ResponseEntity.noContent().build();
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelClassEventById(@PathVariable UUID id) {
        classEventService.cancelClassEventById(id);
        return ResponseEntity.noContent().build();
    }
}