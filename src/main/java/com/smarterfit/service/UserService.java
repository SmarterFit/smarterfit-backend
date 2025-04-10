package com.smarterfit.service;

import com.smarterfit.dto.request.UserRequestDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Profile;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.ProfileRepository;
import com.smarterfit.repository.UserRepository;
import com.smarterfit.util.mapper.UserMapper;
import com.smarterfit.util.validation.ProfileValidation;
import com.smarterfit.util.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        userValidation.validateUsernameAvailability(userRequestDTO.username());
        profileValidation.validateCpfAvailability(userRequestDTO.cpf());

        User user = UserMapper.toEntity(userRequestDTO, null);

        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserByUsername(String username) {
        User user = findUserByUsername(username);
        return UserMapper.toResponse(user);
    }

    @Transactional
    public UserResponseDTO updateUserByUsername(String username, UserRequestDTO requestDTO) {
        User existingUser = findUserByUsername(username);

        if (!existingUser.getEmail().equals(requestDTO.email())) {
            userValidation.validateEmailAvailability(requestDTO.email());
        }

        existingUser = UserMapper.toEntity(requestDTO, existingUser);
        userRepository.save(existingUser);

        return UserMapper.toResponse(existingUser);
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        User user = findUserByUsername(username);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }


}
