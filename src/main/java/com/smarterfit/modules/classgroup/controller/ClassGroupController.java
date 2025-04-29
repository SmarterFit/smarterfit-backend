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

    @PostMapping("/{classGroupId}/planos/{planId}")
    public ResponseEntity<Void> addPlanToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID planId) {
        classGroupService.addPlanToClassGroup(planId, classGroupId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{classGroupId}/usuarios/{userId}/assinatura/{subscriptionId}")
    public ResponseEntity<Void> addUserToClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID userId,
            @PathVariable UUID subscriptionId) {
        classGroupService.addUserToClassGroup(classGroupId, userId, subscriptionId);
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
