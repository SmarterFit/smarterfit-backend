package com.smarterfit.controller;

import com.smarterfit.dto.request.ClassGroupRequestDTO;
import com.smarterfit.dto.response.ClassGroupResponseDTO;
import com.smarterfit.service.ClassGroupService;
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
    public ResponseEntity<ClassGroupResponseDTO> createClassGroup(@RequestBody @Valid ClassGroupRequestDTO classGroupRequest) {
        ClassGroupResponseDTO responseDTO = classGroupService.createClassGroup(classGroupRequest);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassGroupResponseDTO> getClassGroupById(@PathVariable UUID id) {
        return ResponseEntity.ok(classGroupService.getClassGroupById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassGroupResponseDTO> updateClassGroupById(
            @PathVariable UUID id,
            @RequestBody ClassGroupRequestDTO classGroupRequest) {
        return ResponseEntity.ok(classGroupService.updateClassGroupById(id, classGroupRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassGroupById(@PathVariable UUID id) {
        classGroupService.deleteClassGroupById(id);
        return ResponseEntity.noContent().build();
    }

}
