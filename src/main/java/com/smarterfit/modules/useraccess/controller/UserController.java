package com.smarterfit.modules.useraccess.controller;

import com.smarterfit.modules.useraccess.dto.request.user.CreateUserRequestDTO;
import com.smarterfit.modules.useraccess.dto.request.user.UpdateUserEmailRequestDTO;
import com.smarterfit.modules.useraccess.dto.request.user.UpdateUserPasswordRequestDTO;
import com.smarterfit.modules.useraccess.dto.request.user.UpdateUserRolesRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/// TODO: Para criar, editar e deletar usuários é necessário verificar algumas permissões
/// Exemplo: Um usuário com papel diferente de Member só pode ser criado por um usuário com papel igual ou superior.
@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid CreateUserRequestDTO requestDTO) {
        UserResponseDTO responseDTO = userService.createUser(requestDTO);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }



    @GetMapping("/email/{email}")
    public ResponseEntity<List<UserResponseDTO>> searchUsersByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.searchUsersByEmail(email));
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<UserResponseDTO> updateUserEmailById(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserEmailRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.updateUserEmailById(id, requestDTO));
    }

    @PatchMapping("/{id}/senha")
    public ResponseEntity<UserResponseDTO> updateUserPasswordById(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserPasswordRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.updateUserPasswordById(id, requestDTO));
    }

    @PatchMapping("/{id}/cargos")
    public ResponseEntity<UserResponseDTO> updateUserRolesById(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserRolesRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.updateUserRolesById(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
