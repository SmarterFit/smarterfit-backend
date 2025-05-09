package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.modules.classgroup.dto.request.classgroupuser.CreateClassGroupUserDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupUserService;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/turma")
public class ClassGroupUserController {

    public final ClassGroupUserService classGroupUserService;

    public ClassGroupUserController(ClassGroupUserService classGroupUserService) {
        this.classGroupUserService = classGroupUserService;
    }

    @PostMapping("/usuarios/cadastrar")
    public ResponseEntity<Void> addUserToClassGroup(CreateClassGroupUserDTO requestDTO) {
        classGroupUserService.addUserToClassGroup(requestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{classGroupId}/usuarios")
    public ResponseEntity<List<UserResponseDTO>> getUsersByClassGroupId(@PathVariable UUID classGroupId) {
        List<UserResponseDTO> users = classGroupUserService.getUsersByClassGroupId(classGroupId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/usuarios/{userId}")
    public ResponseEntity<List<ClassGroupResponseDTO>> getClassGroupsByUserId(@PathVariable UUID userId) {
        List<ClassGroupResponseDTO> classGroups = classGroupUserService.getClassGroupsByUserId(userId);
        return ResponseEntity.ok(classGroups);
    }

    @DeleteMapping("/{classGroupId}/usuarios/{userId}")
    public ResponseEntity<Void> removeUserFromClassGroup(@PathVariable UUID classGroupId, @PathVariable UUID userId) {
        classGroupUserService.removeUserFromClassGroup(classGroupId, userId);
        return ResponseEntity.noContent().build();
    }
}
