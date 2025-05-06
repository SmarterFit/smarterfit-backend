package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.modules.classgroup.dto.request.classgroup.ClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/turma")
public class ClassGroupController {
    public final ClassGroupService classGroupService;

    public ClassGroupController(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClassGroupResponseDTO> createClassGroup(
            @RequestBody @Valid ClassGroupRequestDTO requestDTO,
            @RequestHeader("X-User-Id") UUID requesterId) {

        ClassGroupResponseDTO responseDTO = classGroupService.createClassGroup(requestDTO, requesterId);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassGroupResponseDTO> getClassGroupById(@PathVariable UUID id) {
        return ResponseEntity.ok(classGroupService.getClassGroupById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassGroupResponseDTO> updateClassGroupById(
            @PathVariable UUID id,
            @RequestBody @Valid ClassGroupRequestDTO requestDTO,
            @RequestHeader("X-User-Id") UUID requesterId) {
        return ResponseEntity.ok(classGroupService.updateClassGroupById(id, requestDTO, requesterId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassGroupById(@PathVariable UUID id,
                                                     @RequestHeader("X-User-Id") UUID requesterId) {
        classGroupService.deleteClassGroupById(id, requesterId);
        return ResponseEntity.noContent().build();
    }



}
