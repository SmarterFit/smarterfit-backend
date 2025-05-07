package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
import com.smarterfit.modules.classgroup.dto.request.classgroup.schedule.CreateClassGroupScheduleRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupScheduleResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupScheduleService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/turma/horarios")
public class ClassGroupScheduleController {

    private final ClassGroupScheduleService classGroupScheduleService;

    public ClassGroupScheduleController(ClassGroupScheduleService classGroupScheduleService) {
        this.classGroupScheduleService = classGroupScheduleService;
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PostMapping("/cadastrar")
    public ResponseEntity<ClassGroupScheduleResponseDTO> createClassGroupSchedule(
            @RequestBody @Valid CreateClassGroupScheduleRequestDTO requestDTO) {

        ClassGroupScheduleResponseDTO responseDTO = classGroupScheduleService
                .createClassGroupSchedule(requestDTO);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassGroupScheduleResponseDTO> getClassGroupScheduleById(@PathVariable UUID id) {
        ClassGroupScheduleResponseDTO responseDTO = classGroupScheduleService.getClassGroupScheduleById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PutMapping("/{id}")
    public ResponseEntity<ClassGroupScheduleResponseDTO> updateClassGroupScheduleById(
            @PathVariable UUID id,
            @RequestBody @Valid CreateClassGroupScheduleRequestDTO requestDTO) {
        ClassGroupScheduleResponseDTO responseDTO = classGroupScheduleService.updateClassGroupScheduleById(id,
                requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @RequireRole(RoleType.EMPLOYEE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassGroupScheduleById(
            @PathVariable UUID id) {

        classGroupScheduleService.deleteClassGroupScheduleById(id);
        return ResponseEntity.noContent().build();
    }

}
