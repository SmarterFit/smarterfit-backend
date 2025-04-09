package com.smarterfit.controller;

import com.smarterfit.dto.request.UserRequestDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequest){
        UserResponseDTO responseDTO = userService.createUser(userRequest);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponseDTO> updateUserByUsername(
            @PathVariable String username,
            @RequestBody UserRequestDTO userRequest) {
        return ResponseEntity.ok(userService.updateUserByUsername(username, userRequest));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
