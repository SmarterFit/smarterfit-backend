package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.modules.classgroup.dto.request.classgroup.CreateClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupService;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/turma")
@CrossOrigin
public class ClassGroupController {
    public final ClassGroupService classGroupService;

    public ClassGroupController(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ClassGroupResponseDTO> createClassGroup(
            @RequestBody @Valid CreateClassGroupRequestDTO requestDTO) {
        ClassGroupResponseDTO responseDTO = classGroupService.createClassGroup(requestDTO);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassGroupResponseDTO> getClassGroupById(@PathVariable UUID id) {
        return ResponseEntity.ok(classGroupService.getClassGroupById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClassGroupResponseDTO>> getAllClassGroup() {
        return ResponseEntity.ok(classGroupService.getAllClassGroups());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassGroupResponseDTO> updateClassGroupById(
            @PathVariable UUID id,
            @RequestBody @Valid CreateClassGroupRequestDTO requestDTO) {
        return ResponseEntity.ok(classGroupService.updateClassGroupById(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassGroupById(@PathVariable UUID id) {
        classGroupService.deleteClassGroupById(id);
        return ResponseEntity.noContent().build();
    }

}
