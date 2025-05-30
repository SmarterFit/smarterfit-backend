package com.smarterfit.modules.classgroup.controller;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.security.RequireRole;
import com.smarterfit.modules.classgroup.dto.request.classgroupuser.EmployeeClassGroupUserDTO;
import com.smarterfit.modules.classgroup.dto.request.classgroupuser.MemberClassGroupUserDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.dto.response.classgroupuser.ClassUsersResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupUserService;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/turma")
public class ClassGroupUserController {

    public final ClassGroupUserService classGroupUserService;

    public ClassGroupUserController(ClassGroupUserService classGroupUserService) {
        this.classGroupUserService = classGroupUserService;
    }

    @RequireRole(RoleType.EMPLOYEE)
    @PostMapping("/professor/cadastrar")
    public ResponseEntity<Void> addEmployeeToClassGroup(@RequestBody @Valid EmployeeClassGroupUserDTO requestDTO,
                                                        @RequestHeader("X-User-Id") UUID requesterId) {
        classGroupUserService.addEmployeeToClassGroup(requestDTO, requesterId);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/alunos/cadastrar")
    public ResponseEntity<Void> addMemberToClassGroup(@RequestBody @Valid MemberClassGroupUserDTO requestDTO) {
        classGroupUserService.addMemberToClassGroup(requestDTO);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{classGroupId}/alunos")
    public ResponseEntity<List<ClassUsersResponseDTO>> getStudentsByClassGroupId(@PathVariable UUID classGroupId) {
        List<ClassUsersResponseDTO> users = classGroupUserService.getStudentsByClassGroupId(classGroupId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{classGroupId}/professores")
    public ResponseEntity<List<ClassUsersResponseDTO>> getTeacherByClassGroupId(@PathVariable UUID classGroupId) {
        List<ClassUsersResponseDTO> users = classGroupUserService.getTeacherByClassGroupId(classGroupId);
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
