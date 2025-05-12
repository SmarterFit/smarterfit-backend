package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
import com.smarterfit.modules.classgroup.dto.request.classgroup.ClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/turma")
public class ClassGroupController {
    public final ClassGroupService classGroupService;

    public ClassGroupController(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @RequireRole(RoleType.TRAINER)
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

    @RequireRole(RoleType.TRAINER)
    @PutMapping("/{id}")
    public ResponseEntity<ClassGroupResponseDTO> updateClassGroupById(
            @PathVariable UUID id,
            @RequestBody @Valid ClassGroupRequestDTO requestDTO) {
        return ResponseEntity.ok(classGroupService.updateClassGroupById(id, requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClassGroupResponseDTO>> getAllClassGroup() {
        return ResponseEntity.ok(classGroupService.getAllClassGroups());
    }

    @RequireRole(RoleType.TRAINER)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassGroupById(@PathVariable UUID id) {
        classGroupService.deleteClassGroupById(id);
        return ResponseEntity.noContent().build();
    }



}
