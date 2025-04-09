package com.smarterfit.service;

import com.smarterfit.dto.request.UserRequestDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.ProfileRepository;
import com.smarterfit.repository.UserRepository;
import com.smarterfit.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        if (userRepository.findByEmail(userRequestDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        if (userRepository.findByUsername(userRequestDTO.username()).isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }

        Profile profile = new Profile();
        User user = UserMapper.toEntity(userRequestDTO, null);
        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponse(user);
    }

    public UserResponseDTO updateUserByUsername(String username, UserRequestDTO requestDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userRepository.findByEmail(requestDTO.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        user = UserMapper.toEntity(requestDTO, user);

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        userRepository.delete(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}
