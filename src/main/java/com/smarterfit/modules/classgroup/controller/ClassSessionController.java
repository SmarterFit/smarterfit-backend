package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.modules.classgroup.dto.request.classsession.CreateClassSessionRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassSessionResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassSessionService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/turma-aula")
public class ClassSessionController {

    public final ClassSessionService classSessionService;

    public ClassSessionController(ClassSessionService classSessionService) {
        this.classSessionService = classSessionService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClassSessionResponseDTO> createClassSession(
            @RequestBody @Valid CreateClassSessionRequestDTO requestDTO) {
        ClassSessionResponseDTO responseDTO = classSessionService.createClassSession(requestDTO);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSessionResponseDTO> getClassSessionById(@PathVariable UUID id) {
        return ResponseEntity.ok(classSessionService.getClassSessionById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClassSessionResponseDTO>> getAllClassSession() {
        return ResponseEntity.ok(classSessionService.getAllClassSession());
    }

    @PostMapping("/agendar")
    public void scheduleClassSession() {
        classSessionService.generateDailySessions();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSessionResponseDTO> updateClassSessionById(
            @PathVariable UUID id,
            @RequestBody @Valid CreateClassSessionRequestDTO requestDTO) {
        return ResponseEntity.ok(classSessionService.updateClassSessionById(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSessionById(@PathVariable UUID id) {
        classSessionService.deleteClassSessionById(id);
        return ResponseEntity.noContent().build();
    }
}
