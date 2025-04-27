package com.smarterfit.modules.useraccess.service;

import com.smarterfit.modules.useraccess.dto.request.user.CreateUserRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.UserMapper;
import com.smarterfit.modules.useraccess.repository.UserRepository;
import com.smarterfit.modules.useraccess.validation.ProfileValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidation userValidation;
    private final ProfileValidation profileValidation;

    @Autowired
    public UserService(UserRepository userRepository, UserValidation userValidation,
            ProfileValidation profileValidation) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
        this.profileValidation = profileValidation;
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDTO requestDTO) {
        userValidation.validatePasswords(requestDTO.password(), requestDTO.confirmPassword());
        userValidation.validateEmailAvailability(requestDTO.email());
        profileValidation.validateCpfAvailability(requestDTO.cpf());

        User user = UserMapper.toEntity(requestDTO);

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID id) {
        User user = userValidation.validateUserById(id);
        return UserMapper.toResponse(user);
    }

    @Transactional
    public UserResponseDTO updateUserById(UUID id, CreateUserRequestDTO requestDTO) {
        User existingUser = userValidation.validateUserById(id);

        if (!existingUser.getEmail().equals(requestDTO.email())) {
            userValidation.validateEmailAvailability(requestDTO.email());
        }

        existingUser = UserMapper.toEntity(requestDTO, existingUser);
        userRepository.save(existingUser);

        return UserMapper.toResponse(existingUser);
    }

    @Transactional
    public void deleteUserById(UUID id) {
        User user = userValidation.validateUserById(id);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}
