package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
import com.smarterfit.modules.classgroup.dto.request.classsession.CreateClassSessionRequestDTO;
import com.smarterfit.modules.classgroup.dto.request.classsession.UpdateClassSessionRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassSessionResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassSessionService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping("/turma/aula")
public class ClassSessionController {

    public final ClassSessionService classSessionService;

    public ClassSessionController(ClassSessionService classSessionService) {
        this.classSessionService = classSessionService;
    }

    @RequireRole(RoleType.EMPLOYEE)
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

    @GetMapping("/agendada/{classGroupId}")
    public ResponseEntity<List<ClassSessionResponseDTO>> getAllClassSessionByClass(@PathVariable UUID classGroupId) {
        return ResponseEntity.ok(classSessionService.getAllClassSessionByGroup(classGroupId));
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PostMapping("/agendar")
    public void scheduleClassSession() {
        classSessionService.generateDailySessions();
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PutMapping("alterar/{id}")
    public ResponseEntity<ClassSessionResponseDTO> updateClassSessionById(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateClassSessionRequestDTO requestDTO) {
        return ResponseEntity.ok(classSessionService.updateClassSessionById(id, requestDTO));
    }

    @RequireRole(RoleType.EMPLOYEE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassSessionById(
            @PathVariable UUID id) {

        classSessionService.deleteClassSessionById(id);
        return ResponseEntity.noContent().build();
    }
}
