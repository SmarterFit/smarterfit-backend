package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.modules.classgroup.dto.request.classgroup.schedule.ClassGroupScheduleRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupScheduleResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupScheduleService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/turma-horarios")
public class ClassGroupScheduleController {

    private final ClassGroupScheduleService classGroupScheduleService;

    public ClassGroupScheduleController(ClassGroupScheduleService classGroupScheduleService) {
        this.classGroupScheduleService = classGroupScheduleService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClassGroupScheduleResponseDTO> createClassGroupSchedule(@RequestBody @Valid ClassGroupScheduleRequestDTO classGroupScheduleDTO) {
        ClassGroupScheduleResponseDTO responseDTO = classGroupScheduleService.createClassGroupSchedule(classGroupScheduleDTO);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassGroupScheduleResponseDTO> getClassGroupScheduleById(@PathVariable UUID id) {
        ClassGroupScheduleResponseDTO responseDTO = classGroupScheduleService.getClassGroupScheduleById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassGroupScheduleResponseDTO> updateClassGroupScheduleById(
            @PathVariable UUID id,
            @RequestBody @Valid ClassGroupScheduleRequestDTO classGroupScheduleDTO) {
        ClassGroupScheduleResponseDTO responseDTO = classGroupScheduleService.updateClassGroupScheduleById(id, classGroupScheduleDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassGroupScheduleById(@PathVariable UUID id) {
        classGroupScheduleService.deleteClassGroupScheduleById(id);
        return ResponseEntity.noContent().build();
    }

}
