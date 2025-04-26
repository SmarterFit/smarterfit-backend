package com.smarterfit.service;

import com.smarterfit.dto.request.UserRequestDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.model.User;
import com.smarterfit.repository.UserRepository;
import com.smarterfit.util.mapper.UserMapper;
import com.smarterfit.util.validation.entity.ProfileValidation;
import com.smarterfit.util.validation.entity.UserValidation;
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
    public UserService(UserRepository userRepository, UserValidation userValidation, ProfileValidation profileValidation) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
        this.profileValidation = profileValidation;
    }
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){

        userValidation.validatePasswords(userRequestDTO.password(), userRequestDTO.confirmPassword());
        userValidation.validateEmailAvailability(userRequestDTO.email());
        profileValidation.validateCpfAvailability(userRequestDTO.cpf());

        User user = UserMapper.toEntity(userRequestDTO);

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID id) {
        User user = userValidation.validateUserById(id);
        return UserMapper.toResponse(user);
    }

    @Transactional
    public UserResponseDTO updateUserById(UUID id, UserRequestDTO requestDTO) {
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
