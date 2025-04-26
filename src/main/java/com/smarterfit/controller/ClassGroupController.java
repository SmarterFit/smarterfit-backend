package com.smarterfit.controller;

import com.smarterfit.dto.request.ClassGroupRequestDTO;
import com.smarterfit.dto.response.ClassGroupResponseDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.service.ClassGroupService;
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
            @RequestBody @Valid ClassGroupRequestDTO classGroupRequest) {
        return ResponseEntity.ok(classGroupService.updateClassGroupById(id, classGroupRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassGroupById(@PathVariable UUID id) {
        classGroupService.deleteClassGroupById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{classGroupId}/planos/{planId}")
    public ResponseEntity<Void> addPlanToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID planId) {
        classGroupService.addPlanToClassGroup(planId, classGroupId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{classGroupId}/usuarios/{userId}")
    public ResponseEntity<Void> addUserToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID userId) {
        classGroupService.addUserToClassGroup(classGroupId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{classGroupId}/usuarios/{userId}")
    public ResponseEntity<Void> removeUserFromClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID userId) {
        classGroupService.removeUserFromClassGroup(classGroupId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{classGroupId}/usuarios")
    public ResponseEntity<List<UserResponseDTO>> getUsersByClassGroupId(@PathVariable UUID classGroupId) {
        List<UserResponseDTO> users = classGroupService.getUsersByClassGroupId(classGroupId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<ClassGroupResponseDTO>> getClassGroupsByUserId(@PathVariable UUID userId) {
        List<ClassGroupResponseDTO> classGroups = classGroupService.getClassGroupByUserId(userId);
        return ResponseEntity.ok(classGroups);
    }

}
