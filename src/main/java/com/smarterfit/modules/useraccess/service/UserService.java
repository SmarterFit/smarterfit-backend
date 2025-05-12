package com.smarterfit.modules.useraccess.service;

import com.smarterfit.common.util.CryptoUtil;
import com.smarterfit.modules.useraccess.dto.request.user.CreateUserRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.entity.Profile;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.UserMapper;
import com.smarterfit.modules.useraccess.repository.UserRepository;
import com.smarterfit.modules.useraccess.validation.ProfileValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final CryptoUtil cryptoUtil;

    @Autowired
    public UserService(UserRepository userRepository,
            UserValidation userValidation,
            ProfileValidation profileValidation,
            PasswordEncoder passwordEncoder,
                       CryptoUtil cryptoUtil) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
        this.profileValidation = profileValidation;
        this.passwordEncoder = passwordEncoder;
        this.cryptoUtil = cryptoUtil;
    }

    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDTO requestDTO) {
        String encryptedCpf = cryptoUtil.encrypt(requestDTO.getCpf());

        userValidation.validatePasswords(requestDTO.getPassword(), requestDTO.getConfirmPassword());
        userValidation.validateEmailAvailability(requestDTO.getEmail());
        profileValidation.validateCpfAvailability(encryptedCpf);

        User user = UserMapper.toEntity(requestDTO, new Profile());

        String encryptedPassword = passwordEncoder.encode(requestDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.getProfile().setCpf(encryptedCpf);

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

        if (!existingUser.getEmail().equals(requestDTO.getEmail())) {
            userValidation.validateEmailAvailability(requestDTO.getEmail());
        }

        existingUser = UserMapper.toEntity(requestDTO, existingUser.getProfile(), existingUser);
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
